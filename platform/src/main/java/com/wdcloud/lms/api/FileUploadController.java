package com.wdcloud.lms.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wdcloud.base.ResponseDTO;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author 赵秀非
 */
@Slf4j
@RestController
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class FileUploadController {

    private final static String FILE_MATCH_REGEX = ".*\\.(ppt|pptx|doc|docx|xls|xlsx)$";

    @Value("${file-server-url}")
    private String fileServerUrl;
    @Autowired
    private OssClient ossClient;

    /**
     * @api {post} /upload 文件上传
     * @apiDescription 文件上传通用接口
     * @apiName upload
     * @apiGroup Common
     *
     * @apiParam {Object} file 上传文件对象
     *
     * @apiSuccess {String} code 响应码 200,500 如果此次请求上传的文件都未上传成功，则返回500；反之则返回200
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {Object} [entity] 文件信息
     * @apiSuccess {String} entity.fileId 文件URL(业务接口上传文件时使用此字段)
     * @apiSuccess {Number} entity.fileSize 文件大小（单位: byte)
     * @apiSuccess {String} entity.fileType 文件类型
     * @apiSuccess {String} entity.originName 文件名称
     *
     * @apiSuccessExample JSON 返回
     * {
     * "code": 200,
     * "entity": [
     * {
     * "fileId": "group1/M00/00/0D/wKgFFFyIhoiAYFKMAAI36YP4DZ0298.jpg",
     * "fileSize": 145385,
     * "fileType": "jpg",
     * "id": 718,
     * "originName": "微信图片_20170613104409.jpg"
     * }
     * ],
     * "message": "success"
     * }
     */
    @PostMapping("/upload")
    public String upload(HttpServletRequest request) throws IOException {
        List<FileInfo> fileInfos = Lists.newArrayList();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        int failCount = 0;
        for (MultipartFile file : files) {
            String upload = ossClient.upload(file.getInputStream(), file.getOriginalFilename());
            ResponseDTO response = JSON.parseObject(upload, ResponseDTO.class);
            if (response.isSuccess()) {
                FileInfo fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
                fileInfos.add(fileInfo);
            }else{
                ++failCount;
            }
        }

        log.info("[FileUpload] upload file finish, fileInfo={}", JSON.toJSONString(fileInfos));
        if(failCount == files.size()){
            return Response.returnResponse(Code.ERROR, "error");
        }else{
            return Response.returnResponse(Code.OK, fileInfos, "success");
        }
    }


    /**
     * @api {get} /ckupload 文件上传-富文本
     * @apiDescription 富文本文件上传
     * @apiName ckupload
     * @apiParam upload
     * @apiGroup Common
     * @apiSuccessExample JSON 返回
     * <p>
     * {
     * "uploaded": 1,
     * "url": "group1/M00/00/0D/wKgFFFyIheGAFz3IAAI36YP4DZ0414.jpg"
     * }
     */
    @PostMapping("/ckupload")
    public Object ckupload(@RequestParam("upload") MultipartFile file) throws IOException {
        String upload = ossClient.upload(file.getInputStream(), file.getOriginalFilename());
        ResponseDTO response = JSON.parseObject(upload, ResponseDTO.class);
        if (response.isSuccess()) {
            FileInfo fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
            JSONObject jo = new JSONObject();
            jo.put("uploaded", 1);
            jo.put("url", fileServerUrl + "/" + fileInfo.getFileId());
            return jo;
        }
        return Response.returnResponse(Code.ERROR, Code.ERROR.name);
    }

    @GetMapping("/docPreview")
    public void docPreview(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        Path download = null;
        if (fileId.matches(FILE_MATCH_REGEX)) {
            int ind = fileId.lastIndexOf(".");
            if (ind > 0) {
                String preview = fileId.substring(0, ind) + "_pdf.pdf";
                download = ossClient.download(preview);
            }
        } else {
            download = ossClient.download(fileId);
        }

        if (download != null) {
            try (OutputStream outputStream = response.getOutputStream()) {
                Files.copy(download, outputStream);
            } catch (Exception e) {
                throw new BaseException(e);
            }
        } else {
            throw new BaseException();
        }
    }

    /**
     * @api {get} /image 文件访问规则
     * @apiDescription
     * 图片/文件地址: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg <br/>
     * 图片裁剪: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=thumb&a=64x64 <br/>
     * 图偏拉伸: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=resize&w=800&h=800 <br/>
     * 图片旋转: http://192.168.6.166/group1/M00/00/0D/wKgFFFyGSkqAYB_9AA12C5JxxdA516.jpg?s=rotate&d=90 <br/>
     * @apiName FileImageSASSSS
     * @apiGroup Common
     */
}

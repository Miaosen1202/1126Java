package com.wdcloud.lms.business.file;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDirectComponent;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@IDirectComponent.DirectHandler(
        resource = Constants.RESOURCE_TYPE_USER_FILE,
        function = Constants.FUNCTION_TYPE_DOWNLOAD,
        description = "文件，文件夹下载"
)
public class UserFileDownloadEdit implements IDirectComponent {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private OssClient ossClient;
    @Autowired
    private UserFileService userFileService;

    /**
     * @api {get} /userFile/download/direct 文件下载
     * @apiDescription 下载文件或文件夹，文件夹打包下载
     * @apiName userFileDownload
     * @apiGroup Common
     *
     * @apiParam {Number} id 文件、文件夹ID
     *
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isEmpty(request.getParameter(Constants.PARAM_ID))) {
            throw new ParamErrorException();
        }
        long id = Long.parseLong(request.getParameter(Constants.PARAM_ID));

        UserFile userFile = userFileDao.get(id);

        if (Objects.equals(userFile.getIsDirectory(), Status.YES.getStatus())) {
            // 文件打包
            Map<Long, Path> fileMap = new HashMap<>();

            String accessPublishedSQL = userFileService.accessPublishedSQL(userFile);
            String accessUnpublishedSQL = userFileService.accessUnpublishedSQL(userFile);
            String accessRestrictedSQL = userFileService.accessRestrictedSQL(userFile);

            Example example = userFileDao.getExample();
            Example.Criteria criteria = example.createCriteria()
                    .andLike(UserFile.TREE_ID, userFile.getTreeId() + "%")
                    .andCondition("(" + accessPublishedSQL + " or (" + accessUnpublishedSQL + ") or ("
                            + accessRestrictedSQL + "))");
            List<UserFile> files = userFileDao.find(example);

            //除去顶层文件夹
            Map<Long, UserFile> fileIdMap = files.stream()
                    .collect(Collectors.toMap(UserFile::getId, a -> a));

            for (UserFile file : files) {
                if (Objects.equals(file.getIsDirectory(), Status.NO.getStatus())) {
                    Path downloadFile = ossClient.download(file.getFileUrl());
                    fileMap.put(file.getId(), downloadFile);
                }
            }

            setDownloadName(response, userFile.getFileName() + ".zip");
            try (ZipOutputStream zout = new ZipOutputStream(response.getOutputStream());) {
                zout.setEncoding("utf-8");
                for (UserFile file : files) {
                    try {
                        Path path = fileMap.get(file.getId());
                        if (path == null) {
                            log.error("[UserFileDownloadEdit] zip file not exists, fileId={}, fileName={}", file.getId(), file.getFileName());
                            continue;
                        }

                        //zipEntryName为路径+文件名
                        UserFile parentFile = fileIdMap.get(file.getParentId());
                        StringBuffer zipEntryName = new StringBuffer();
                        boolean flag = false;
                        while(null != parentFile){
                            flag = true;

                            zipEntryName.insert(0, parentFile.getFileName())
                                        .insert(0, "/");
                            parentFile = fileIdMap.get(parentFile.getParentId());
                        }

                        if(flag){
                            zipEntryName.append("/");
                        }

                        String fileName = file.getFileName();
                        if(StringUtil.isEmpty(file.getFileName())){
                            fileName = UUID.randomUUID().toString();
                        }
                        zipEntryName.append(file.getFileName());

                        ZipEntry zipEntry = new ZipEntry(zipEntryName.toString());
                        zipEntry.setSize(Files.size(path));
                        zipEntry.setTime(Files.getLastModifiedTime(path).toMillis());
                        zout.putNextEntry(zipEntry);

                        writeFile(path, zout);
                    } catch (IOException e) {
                        log.error("[UserFileDownloadEdit] zip file fail, fileId={}, fileUrl={}", file.getId(), file.getFileUrl());
                    }
                }
            } catch (IOException e) {
                log.error("[UserFileDownloadEdit] open zip file fail");
            }
        } else {
            Path download = ossClient.download(userFile.getFileUrl());
            setDownloadName(response, userFile.getFileName());

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                writeFile(download, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDownloadName(HttpServletResponse response, String name) {
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
    }

    private void writeFile(Path target, OutputStream outputStream) {
        if (Files.exists(target) && Files.isRegularFile(target)) {
            try (InputStream inputStream = new BufferedInputStream(new FileInputStream(target.toFile()))) {
                int len;
                byte[] buff = new byte[2048];
                while ((len = inputStream.read(buff)) > 0) {
                    outputStream.write(buff, 0, len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.wdcloud.lms.base.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.wdcloud.base.ResponseDTO;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.ResourceFileTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ResourceCommonService {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private ResourceFileDao resourceFileDao;
    @Autowired
    private ResourceImportDao resourceImportDao;
    @Autowired
    private ResourceVersionDao resourceVersionDao;
    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    @Autowired
    private ResourceImportGenerationDao resourceImportGenerationDao;
    @Autowired
    private ResourceCourseImportAssignmentGroupDao courseImportAssignmentGroupDao;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private OssClient ossClient;

    private final static String GROUP_NAME = "Imported List";

    /**
     * 逗号分隔的字符串转list
     * @param string
     * @return
     */
    public List<String> convertList(String string){
        List<String> types = null;
        if(StringUtil.isNotEmpty(string)){
            types = Arrays.asList(string.split(","));
        }
        return types;
    }

    /**
     * 查找课程的资源导入作业组id
     * @param courseId
     * @return
     */
    public Long getAssignmentGroupId(Long courseId){
        ResourceCourseImportAssignmentGroup courseGroup = courseImportAssignmentGroupDao.getByCourseId(courseId);
        AssignmentGroup assignmentGroup;
        Long assignmentGroupId;

        if(Objects.isNull(courseGroup)){
            //首次在该课程下导入
            assignmentGroupId = creatImportGroup(courseId);
            courseGroup = ResourceCourseImportAssignmentGroup.builder()
                    .assignmentGroupId(assignmentGroupId).courseId(courseId).build();
            courseImportAssignmentGroupDao.save(courseGroup);
        }else{
            //存在importList小组
            assignmentGroupId = courseGroup.getAssignmentGroupId();
            //小组是否还在，可能在小组页面被删除
            assignmentGroup = assignmentGroupDao.get(assignmentGroupId);
            if(Objects.isNull(assignmentGroup)){
                assignmentGroupId = creatImportGroup(courseId);
                courseGroup.setAssignmentGroupId(assignmentGroupId);
                courseImportAssignmentGroupDao.update(courseGroup);
            }
        }

        return assignmentGroupId;
    }

    /**
     * 单个删除时，校验参数
     * @param ids
     * @return
     */
    public Long singleDeleteValid(List<Long> ids){
        if(1 != ids.size()){
            throw new ParamErrorException();
        }
        return ids.get(0);
    }

    /**
     * 从服务器获取数据
     * @param resourceId
     * @return
     */
    public String getResourceData(Long resourceId){
        String fileId = resourceFileDao.getFileUrlByCondition(resourceId, ResourceFileTypeEnum.DATA.getType(), "txt");
        Path path = ossClient.download(fileId);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path.toString())), "UTF-8"));
            String lineTxt;
            while ((lineTxt = bfr.readLine()) != null) {
                result.append(lineTxt).append("\n");
            }
            bfr.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取年级范围的最小值
     * @param gradeStart
     * @return
     */
    public Integer getGradeStart(Integer gradeStart){
        String temp = Integer.toBinaryString(gradeStart);
        return temp.length()-temp.lastIndexOf("1")-1;
    }

    /**
     * 获取年级范围的最大值
     * @param gradeEnd
     * @return
     */
    public Integer getGradeEnd(Integer gradeEnd){
        String temp = Integer.toBinaryString(gradeEnd);
        return temp.length()-temp.indexOf("1")-1;
    }

    /**
     * 从文件服务器备份、复制文件
     * @param id
     * @param resourceId
     */
    public Long copyFileToResourceFile(Long id, Long resourceId, Integer type){
        UserFile userFile = userFileDao.get(id);
        FileInfo fileInfo = userFileService.getFileInfo(userFile.getFileUrl());

        getFileInfoInOss(fileInfo.getFileId(), fileInfo.getFileType());
        Long resourceVersionId =  resourceVersionDao.getLatestByResourceId(resourceId).getId();

        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId).versionId(resourceVersionId)
                .type(type).fileName(userFile.getFileName()).fileType(fileInfo.getFileType())
                .fileSize(fileInfo.getFileSize()).fileUrl(fileInfo.getFileId()).build();
        resourceFileDao.save(resourceFile);
        return resourceFile.getId();
    }

    /**
     * 将资源文件备份，并保存至userFile表
     * @param attachmentFileId
     * @param courseId
     * @return
     */
    public Long copyAndSaveFile(Long attachmentFileId, Long courseId){
        ResourceFile resourceFile = resourceFileDao.get(attachmentFileId);

        FileInfo fileInfo = getFileInfoInOss(resourceFile.getFileUrl(), resourceFile.getFileType());
        fileInfo.setOriginName(resourceFile.getFileName());
        UserFile userFile = userFileService.saveCourseContentAttachment(fileInfo, courseId);
        return userFile.getId();
    }

    /**
     * 从中间表获取导入数据和生成数据的关系记录
     * @param resourceId
     * @param courseId
     * @param originId
     * @param type
     * @return
     */
    public ResourceImportGeneration getResourceImportGenerationByOriginId(Long resourceId,
                                    Long courseId, Long originId, Integer type){
        ResourceImport resourceImport = resourceImportDao.getResourceIdAndCourseId(resourceId, courseId);
        return resourceImportGenerationDao.getByImportIdAndOriginIdAndType(resourceImport.getId(), originId, type);
    }

    public ResourceImportGeneration getResourceImportGenerationByNewId(Long resourceId, Long courseId, Long newId, Integer type) {
        ResourceImport resourceImport = resourceImportDao.getResourceIdAndCourseId(resourceId, courseId);
        return resourceImportGenerationDao.getByImportIdAndNewIdAndType(resourceImport.getId(), newId, type);
    }

    /**
     * 转换资源中的年级
     * @param resourceVOS
     * @return
     */
    public Page<ResourceVO> convertGrade(Page<ResourceVO> resourceVOS){
        if(ListUtils.isNotEmpty(resourceVOS)){
            resourceVOS.forEach(resourceVO -> {
                if(Objects.nonNull(resourceVO.getGrade())){
                    resourceVO.setGradeStart(getGradeStart(resourceVO.getGrade()));
                    resourceVO.setGradeEnd(getGradeEnd(resourceVO.getGrade()));
                }
            });
        }
        return resourceVOS;
    }

    /**
     * 创建资源导入时的小组
     * @param courseId
     * @return
     */
    private Long creatImportGroup(Long courseId){
        AssignmentGroup assignmentGroup = AssignmentGroup.builder().courseId(courseId).name(GROUP_NAME).build();
        assignmentGroupDao.save(assignmentGroup);
        return assignmentGroup.getId();
    }

    /**
     * 冲文件服务器获取文件
     * @param fileId
     * @param fileType
     * @return
     */
    private FileInfo getFileInfoInOss(String fileId, String fileType){
        Path path = ossClient.download(fileId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Files.copy(path, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String upload =  ossClient.upload(byteArrayInputStream, UUID.randomUUID().toString() + "." + fileType);
        ResponseDTO response = JSON.parseObject(upload, ResponseDTO.class);
        if (response.isSuccess()) {
            FileInfo fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
            return fileInfo;
        }else{
            throw new BaseException();
        }
    }

}

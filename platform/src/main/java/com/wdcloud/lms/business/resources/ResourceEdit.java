package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdcloud.base.ResponseDTO;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.resources.dto.ResourceShareDTO;
import com.wdcloud.lms.business.resources.enums.ResourceModuleTypeEnum;
import com.wdcloud.lms.business.resources.enums.ResourceLogOperationTypeEnum;
import com.wdcloud.lms.business.resources.enums.ThumbnailOriginTypeEnum;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.ResourceFileTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author  WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES, description = "资源分享")
public class ResourceEdit implements IDataEditComponent {

    @Value("${file-server-url}")
    private String fileServerUrl;

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceTagDao resourceTagDao;
    @Autowired
    private ResourceFileDao resourceFileDao;
    @Autowired
    private ResourceUpdateDao resourceUpdateDao;
    @Autowired
    private ResourceVersionDao resourceVersionDao;
    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;

    @Autowired
    private UserFileService userFileService;
    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;

    @Autowired
    private OssClient ossClient;
    @Autowired
    private StrategyFactory strategyFactory;

    /**
     * @api {post} /resource/add 资源分享
     * @apiDescription 资源分享
     * @apiName resourceAdd
     * @apiGroup resource
     *
     * @apiParam {Number} originId 来源ID
     * @apiParam {Number=1,2,3} shareRange 分享范围， 1：自己，2：机构，3：公开
     * @apiParam {Number=1,2,3,4,5,6,7} licence 版权，1：Public Domain，2：CC-Attribution，3：CC-Attribution ShareAlike，
     * 4：CC-Attribution NoDerivs，5：CC-Attribution NonCommercial，6：CC-Attribution NonCommercial ShareAlike，
     * 7：CC-Attribution NonCommercial NoDerivs
     * @apiParam {String} name 名称
     * @apiParam {String} description 简介
     * @apiParam {String[]} [tags] 标签
     * @apiParam {String} fileUrl 文件在服务器的位置fieldId
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeStart] 年级开始，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeEnd] 年级结束，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
     * @apiParam {Number=1,2,3,15} originType 类别，1：作业，2：讨论，3：测验，15：课程
     * @apiParam {Number} [updateId] 更新资源id，当更新之前分享的资源时必填
     * @apiParam {String} [versionNotes] 版本变化的描述，当更新之前分享的资源时必填
     * @apiExample {json} 请求示例:
     * {
     * 	   "originId":89,
     *     "shareRange":3,
     *     "licence":6,
     *     "name":"是分享作业呀",
     *     "description":"是分享作业的介绍呀",
     *     "tags":["作业","分享"],
     *     "fileUrl":"group1/M00/00/2F/wKgFFF0evV2ARE5-AAAOCcTFS-s024.png",
     *     "gradeStart":2,
     *     "gradeEnd":7,
     *     "originType":1
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 新增ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "11"
     * }
     */
    @AccessLimit
    @ResourceLog(module = ResourceModuleTypeEnum.UPDATE, operation = ResourceLogOperationTypeEnum.Add)
    @ValidationParam(clazz = ResourceShareDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();

        final ResourceShareDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceShareDTO.class);
        Long resourceId = dto.getUpdateId();
        Integer grade = dealGrade(dto.getGradeStart(), dto.getGradeEnd());
        Resource resource = BeanUtil.copyProperties(dto, Resource.class);

        Long resourceVersionId;
        ResourceUpdate resourceUpdate;

        if(Objects.isNull(resourceId)){
            //上传新的资源
            resource.setOrgId(WebContext.getOrgId());
            resource.setAuthorId(WebContext.getUserId());
            resourceDao.save(resource);
            resourceId = resource.getId();
            resourceVersionId = addVersion(resourceId, dto.getVersionNotes());

            resourceUpdate = ResourceUpdate.builder().resourceId(resourceId)
                    .shareRange(dto.getShareRange())
                    .versionId(resourceVersionId).licence(dto.getLicence())
                    .name(dto.getName()).grade(grade) .description(dto.getDescription())
                    .build();
            resourceUpdateDao.save(resourceUpdate);
        }else{
            if(StringUtil.isEmpty(dto.getVersionNotes())){
                throw new ParamErrorException();
            }

            resourceVersionId = addVersion(resourceId, dto.getVersionNotes());
            Long resourceUpdateId = resourceUpdateDao.getByResourceId(resourceId).getId();

            //更新
            resourceUpdate = ResourceUpdate.builder().id(resourceUpdateId).resourceId(resourceId)
                    .shareRange(dto.getShareRange()).versionId(resourceVersionId).licence(dto.getLicence())
                    .name(dto.getName()).grade(grade) .description(dto.getDescription())
                    .build();
            resourceUpdateDao.update(resourceUpdate);
        }
        saveTags(resourceId, dto.getTags());

        //封面图
        FileInfo fileInfo = userFileService.getFileInfo(dto.getFileUrl());
        saveResourceFile(resourceId, resourceVersionId, ResourceFileTypeEnum.COVER.getType(), fileInfo);

        //查询上传数据
        QueryStrategy queryStrategy = strategyFactory.getQueryStrategy(OriginTypeEnum.typeOf(dto.getOriginType()));
        String shareInfo = queryStrategy.queryResourceShareInfo(dto.getOriginId(), resourceId, dto.getName());

        String upload = ossClient.upload(new ByteArrayInputStream(shareInfo.getBytes()), UUID.randomUUID().toString()+".txt");
        ResponseDTO response = JSON.parseObject(upload, ResponseDTO.class);
        if (response.isSuccess()) {
            fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
            saveResourceFile(resourceId, resourceVersionId, ResourceFileTypeEnum.DATA.getType(), fileInfo);
        }else{
            throw new BaseException("upload.fail");
        }

        setRemind(resourceId);

        return new LinkedInfo(String.valueOf(resourceId));
    }

    /**
     * @api {post} /resource/modify 资源基础信息编辑
     * @apiDescription 资源基础信息编辑
     * @apiName resourceModify
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源ID
     * @apiParam {Number=1,2,3,4,5,6,7} licence 版权，1：Public Domain，2：CC-Attribution，3：CC-Attribution ShareAlike，
     * 4：CC-Attribution NoDerivs，5：CC-Attribution NonCommercial，6：CC-Attribution NonCommercial ShareAlike，
     * 7：CC-Attribution NonCommercial NoDerivs
     * @apiParam {Number=1,2,3} shareRange 分享范围， 1：自己，2：机构，3：公开
     * @apiParam {String} name 名称
     * @apiParam {String} description 简介
     * @apiParam {String} versionNotes 版本变化的描述，当更新之前分享的资源时必填
     * @apiParam {String[]} [tags] 标签
     * @apiParam {String} fileUrl 文件在服务器的位置fieldId
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeStart] 年级开始，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeEnd] 年级结束，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
     * @apiExample {json} 请求示例:
     * {
     * 	   "id":4,
     *     "licence":6,
     *     "shareRange":3,
     *     "name":"是修改呀",
     *     "description":"是分享作业的介绍呀",
     *     "versionNotes":"我要修改",
     *     "tags":["作业","分享"],
     *     "fileUrl":"group1/M00/00/2F/wKgFFF0evV2ARE5-AAAOCcTFS-s024.png",
     *     "gradeStart":2,
     *     "gradeEnd":7
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 修改ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "4"
     * }
     */
    @ResourceLog(module = ResourceModuleTypeEnum.UPDATE, operation = ResourceLogOperationTypeEnum.EDIT)
    @ValidationParam(clazz = ResourceShareDTO.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        roleValidateService.teacherAndAdminValid();
        final ResourceShareDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceShareDTO.class);

        Long resourceId = dto.getId();
        Long resourceUpdateId = resourceUpdateDao.getByResourceId(resourceId).getId();

        Integer grade = dealGrade(dto.getGradeStart(), dto.getGradeEnd());
        ResourceUpdate resourceUpdate = ResourceUpdate.builder().id(resourceUpdateId)
                .licence(dto.getLicence()).shareRange(dto.getShareRange())
                .name(dto.getName()).description(dto.getDescription()).grade(grade).build();
        resourceUpdateDao.update(resourceUpdate);

        ResourceVersion resourceVersion = resourceVersionDao.getLatestByResourceId(resourceId);
        Long resourceVersionId = resourceVersion.getId();
        resourceVersion.setDescription(dto.getVersionNotes());
        resourceVersionDao.updateIncludeNull(resourceVersion);

        saveTags(resourceId, dto.getTags());

        FileInfo fileInfo = userFileService.getFileInfo(dto.getFileUrl());
        saveResourceFile(resourceId, resourceVersionId, ResourceFileTypeEnum.COVER.getType(), fileInfo);

        return new LinkedInfo(String.valueOf(resourceUpdate.getId()));
    }

    /**
     * @api {post} /resource/deletes 资源删除
     * @apiDescription 资源删除
     * @apiName resourceDelete
     * @apiGroup resource
     *
     * @apiParam {Number[]} ids 资源id
     * @apiExample {json} 请求示例:
     * [4]
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccessExample {json} 响应示例：
     * {
     *      "code": 200,
     *      "message": "4"
     *  }
     *
     */
    @AccessLimit
    @ResourceLog(module = ResourceModuleTypeEnum.UPDATE, operation = ResourceLogOperationTypeEnum.DELETE)
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        roleValidateService.teacherAndAdminValid();
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        Long resourceId = resourceCommonService.singleDeleteValid(ids);

        Resource resource = resourceDao.get(resourceId);
        if(Objects.isNull(resource)){
            throw new BaseException("prop.value.not-exists", "id", String.valueOf(resourceId));
        }
        resourceDao.delete(resourceId);

        ResourceVersion resourceVersion = ResourceVersion.builder().resourceId(resourceId).build();
        resourceVersionDao.deleteByField(resourceVersion);

        ResourceUpdate resourceUpdate = resourceUpdateDao.getByResourceId(resourceId);
        resourceUpdateDao.delete(resourceUpdate.getId());

        ResourceTag resourceTag = ResourceTag.builder().resourceId(resourceId).build();
        resourceTagDao.deleteByField(resourceTag);

        ResourceVersionMessage resourceVersionMessage = ResourceVersionMessage.builder().resourceId(resourceId).build();
        resourceVersionMessageDao.deleteByField(resourceVersionMessage);

        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId).build();
        List<ResourceFile> resourceFiles = resourceFileDao.find(resourceFile);
        //文件服务器上的文件删除
        for(ResourceFile temp : resourceFiles){
            ossClient.delete(temp.getFileUrl());
            ossClient.delete(temp.getThumbnailUrl());
        }
        resourceFileDao.deleteByField(resourceFile);

        return new LinkedInfo(String.valueOf(ids.size()));
    }

    /**
     * 保存版本信息
     * @param resourceId
     * @param versionNotes
     * @return
     */
    private Long addVersion(Long resourceId, String versionNotes){

        ResourceVersion resourceVersion = ResourceVersion.builder().resourceId(resourceId)
                .description(versionNotes).build();
        resourceVersionDao.save(resourceVersion);

        return resourceVersion.getId();
    }

    /**
     * 保存标签
     * @param resourceId
     * @param tags
     */
    private void saveTags(Long resourceId, List<String> tags){
        if(ListUtils.isEmpty(tags)){
            return;
        }

        ResourceTag originResourceTag = ResourceTag.builder().resourceId(resourceId).build();
        List<ResourceTag> originResourceTags = resourceTagDao.find(originResourceTag);
        List<String> originTags = originResourceTags.stream().map(ResourceTag::getName).collect(Collectors.toList());

        List<String> notexists =  new ArrayList<String>(originTags);
        notexists.removeAll(tags);
        tags.removeAll(originTags);

        List<Long> ids = originResourceTags.stream().filter(t -> notexists.contains(t.getName()))
                .map(ResourceTag :: getId).collect(Collectors.toList());
        resourceTagDao.deletes(ids);

        List<ResourceTag> resourceTags = new ArrayList<ResourceTag>();
        for(String tag : tags){
            ResourceTag resourceTag = ResourceTag.builder().resourceId(resourceId)
                    .name(tag).createUserId(WebContext.getUserId()).updateUserId(WebContext.getUserId()).build();
            resourceTags.add(resourceTag);
        }
        resourceTagDao.batchInsert(resourceTags);
    }

    /**
     * 保存文件,此处处理的文件只包括封面图和数据两种类型
     * @param resourceId
     * @param resourceVersionId
     * @param type
     * @param fileInfo
     */
    private void saveResourceFile(Long resourceId, Long resourceVersionId, Integer type,
                                  FileInfo fileInfo){
        ResourceFile origin;
        if(ResourceFileTypeEnum.COVER.getType() == type){
            origin = resourceFileDao.getByCondition(resourceId, type);
            //为封面图时，图片如果没有更新过，则不处理，否则重新生成缩略图
            if(Objects.nonNull(origin) && origin.getThumbnailUrl().equals(fileInfo.getFileId())){
                return ;
            }
        }else{
            origin = resourceFileDao.getByCondition(resourceId, type, fileInfo.getFileType());
            //文件相同则不再复制
            if(Objects.nonNull(origin) && origin.getFileUrl().equals(fileInfo.getFileId())){
                return ;
            }
        }

        String thumbnailUrl = null;
        //此时一定有封面图
        if(ResourceFileTypeEnum.COVER.getType() == type){
            thumbnailUrl = getThumbnail(fileInfo.getFileId(), fileInfo.getFileType());
        }
        Integer thumbnailOriginType = StringUtil.isEmpty(thumbnailUrl) ? null : ThumbnailOriginTypeEnum.UPLOAD.getType();

        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId).versionId(resourceVersionId)
                .type(type).fileName(fileInfo.getOriginName()).fileType(fileInfo.getFileType())
                .fileSize(fileInfo.getFileSize()).fileUrl(fileInfo.getFileId())
                .thumbnailUrl(thumbnailUrl).thumbnailOriginType(thumbnailOriginType).build();

        if(Objects.isNull(origin)){
            resourceFileDao.save(resourceFile);
        }else{
            resourceFile.setId(origin.getId());
            resourceFileDao.update(resourceFile);
        }
    }

    /**
     * 生成缩略图
     * @param fileUrl
     * @param fileType
     * @return
     */
    private String getThumbnail(String fileUrl, String fileType) {
        BufferedImage image = null;
        InputStream inputStream = null;

        Path path = ossClient.download(fileUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Files.copy(path, outputStream);
            ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());

            image = Thumbnails.of(byteArrayInputStream).scale(0.5f).asBufferedImage();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, fileType, os);
            inputStream = new ByteArrayInputStream(os.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        String upload =  ossClient.upload(inputStream, UUID.randomUUID().toString() + "." + fileType);
        ResponseDTO response = JSON.parseObject(upload, ResponseDTO.class);
        if (response.isSuccess()) {
            FileInfo fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
            return fileInfo.getFileId();
        }else{
            throw new BaseException();
        }
    }

    /**
     * 位图处理年级
     * @param gradeStart
     * @param gradeEnd
     * @return
     */
    private Integer dealGrade(Integer gradeStart, Integer gradeEnd){
        Integer grade = null;
        Double temp = null;
        if(Objects.nonNull(gradeStart)){
            temp = Math.pow(2, gradeStart);
        }
        if(Objects.nonNull(gradeEnd)){
            temp += Math.pow(2, gradeEnd);
        }
        if(Objects.nonNull(temp)){
            grade = Integer.valueOf(temp.intValue());
        }
        return  grade;
    }

    private void setRemind(Long resourceId){
        List<ResourceVersionMessage> versionMessages = resourceVersionMessageDao.findByResourceId(resourceId);
        if(ListUtils.isNotEmpty(versionMessages)){
            List<Long> ids = new ArrayList<>();
            versionMessages.forEach(m -> {
                ids.add(m.getId());
            });
            ResourceVersionMessage versionMessage = ResourceVersionMessage.builder().resourceId(resourceId)
                    .isRemind(Status.YES.getStatus()).build();
            resourceVersionMessageDao.updateByExample(versionMessage, ids);
        }
    }
}

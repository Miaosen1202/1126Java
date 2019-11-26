package com.wdcloud.lms.business.file;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.vo.UserFileEditVo;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileSpaceTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文件、文件夹操作
 *
 * role         space           dir                                                     file
 * TEACHER/     personal        add/rename/remove/delete/status/download                upload/download/rename/move/delete/preview/status
 * TUTOR        course          add/rename/remove/delete/status/download                upload/download/rename/move/delete/preview/status/share
 *              study group     -
 *
 * STUDENT      personal        add/rename/remove/delete/status/download                upload/download/rename/move/delete/preview/status
 *              course          download                                                download/preview
 *              study group     add/rename/move/delete/download                         upload/download/rename/move/delete/preview
 */
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_FILE, description = "文件编辑")
public class UserFileDataEdit implements IDataEditComponent {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     *
     * @api {post} /userFile/add 文件添加
     * @apiDescription 添加文件或文件夹；文件分个人、课程、学习小组三种空间，学生可以对个人、学习小组空间进行操作，教师可以对ｇｅ;
     * 当非文件夹操作是覆盖时，再次判断非文件夹是否存在，如果存在则覆盖，不存在则新增；当非文件夹操作是默认操作（非覆盖）时，
     * 判断文件是否存在，如果存在则重命名加-Copy，否则直接保存
     * @apiName UserFileAdd
     * @apiGroup Common
     *
     * @apiParam {Number=0,1} isDirectory 是否为文件夹
     * @apiParam {String} name 文件名称
     * @apiParam {String} [fileId] 文件ID
     * @apiParam {Number} parentDirectoryId 上级文件夹ID
     * @apiParam {Number=0,1} [isCovered] 是否覆盖原有文件
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 文件ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "vo": "1"
     * }
     *
     * @param dataEditInfo
     * @return
     */
    @ValidationParam(clazz = UserFileEditVo.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        UserFileEditVo userFileEditVo = JSON.parseObject(dataEditInfo.beanJson, UserFileEditVo.class);
        String fileName = userFileEditVo.getName();
        Integer status = userFileEditVo.getStatus();
        Integer isDirectoryInt = userFileEditVo.getIsDirectory();
        Long parentDirectoryId = userFileEditVo.getParentDirectoryId();
        UserFile parent = userFileDao.findOne(UserFile.builder().id(parentDirectoryId).build());
        if (parent == null) {
            throw new BaseException("prop.value.not-exists", "parentDirectoryId", String.valueOf(parentDirectoryId));
        }

        if(StringUtil.isEmpty(userFileEditVo.getName())){
            throw new BaseException();
        }

        if (Objects.equals(parent.getAllowUpload(), Status.NO.getStatus())) {
            throw new PermissionException();
        }

        boolean nameExist = userFileDao.isNameExists(parentDirectoryId, fileName, isDirectoryInt);
        Integer isCovered = userFileEditVo.getIsCovered();
        boolean isCoveredBoolean = false;
        if(null != isCovered && (isCovered.equals(Status.YES.getStatus()))){
            isCoveredBoolean = true;
        }

        UserFile newUserFile = null;
        status = Objects.isNull(status) ? Status.YES.getStatus() : status;

        //只有文件会覆盖，不会上传文件夹
        Status isDir = Status.statusOf(isDirectoryInt);
        UserFile rootDirectory = userFileService.getFileRootDirectory(parent.getTreeId());
        FileSpaceTypeEnum fileSpace = FileSpaceTypeEnum.typeOf(rootDirectory.getSpaceType());
        switch (fileSpace) {
            case PERSONAL:
                if (!Objects.equals(rootDirectory.getUserId(), WebContext.getUserId())) {
                    log.info("[UserFileDataEdit] user can't upload file or create dir on other's personal space, targetUserSpace={}, opUserId={}",
                            rootDirectory.getUserId(), WebContext.getUserId());
                    throw new PermissionException();
                }

                if (isDir == Status.NO) {
                    FileInfo fileInfo = userFileService.getFileInfo(userFileEditVo.getFileId());
                    if (fileInfo == null) {
                        throw new BaseException("prop.value.not-exists", "fileId", String.valueOf(userFileEditVo.getFileId()));
                    }

                    //覆盖且重名，则替换原有文件
                    if(isCoveredBoolean && nameExist){
                        newUserFile = userFileService.coveredFilePackage(parentDirectoryId, userFileEditVo.getName(), fileInfo);
                    }else{
                        //不覆盖且重名，名字自动加后缀
                        if (nameExist && !isCoveredBoolean) {
                            String newName = userFileService.getNewFileName(fileName, fileInfo.getId(),  parentDirectoryId, isDirectoryInt);
                            userFileEditVo.setName(newName);
                        }

                        //覆盖且不重名、不覆盖且不重名（大多数情况）
                        UserFile userFile = UserFile.builder()
                                .spaceType(fileSpace.getType())
                                .userId(WebContext.getUserId())
                                .isDirectory(isDir.getStatus())
                                .fileName(userFileEditVo.getName())
                                .fileType(fileInfo.getFileType())
                                .fileSize(fileInfo.getFileSize())
                                .fileUrl(fileInfo.getFileId())
                                .parentId(parent.getId())
                                .treeId(userFileService.getNextTreeId(parent))
                                .status(status)
                                .build();
                        newUserFile = userFile;
                    }
                } else {
                    UserFile userFile = UserFile.builder()
                            .spaceType(fileSpace.getType())
                            .userId(WebContext.getUserId())
                            .isDirectory(isDir.getStatus())
                            .fileName(userFileEditVo.getName())
                            .parentId(parent.getId())
                            .treeId(userFileService.getNextTreeId(parent))
                            .status(status)
                            .build();
                    newUserFile = userFile;
                }
                break;
            case COURSE:
                Long courseId = rootDirectory.getCourseId();
                if (!roleService.hasTeacherOrTutorRole()) {
                    throw new PermissionException();
                }

                if (isDir != Status.YES) {
                    FileInfo fileInfo = userFileService.getFileInfo(userFileEditVo.getFileId());
                    if (fileInfo == null) {
                        throw new BaseException("prop.value.not-exists", "fileId", String.valueOf(userFileEditVo.getFileId()));
                    }

                    //覆盖且重名，则替换原有文件
                    if(isCoveredBoolean && nameExist){
                        newUserFile = userFileService.coveredFilePackage(parentDirectoryId, userFileEditVo.getName(), fileInfo);
                    }else{
                        //不覆盖且重名，名字自动加后缀
                        if (nameExist && !isCoveredBoolean) {
                            String newName = userFileService.getNewFileName(fileName, fileInfo.getId(),  parentDirectoryId, isDirectoryInt);;
                            userFileEditVo.setName(newName);
                        }

                        //覆盖且不重名、不覆盖且不重名（大多数情况）
                        UserFile userFile = UserFile.builder()
                                .spaceType(fileSpace.getType())
                                .courseId(courseId)
                                .isDirectory(isDir.getStatus())
                                .fileName(userFileEditVo.getName())
                                .fileType(fileInfo.getFileType())
                                .fileSize(fileInfo.getFileSize())
                                .fileUrl(fileInfo.getFileId())
                                .parentId(parent.getId())
                                .treeId(userFileService.getNextTreeId(parent))
                                .status(status)
                                .build();
                        newUserFile = userFile;
                    }
                } else {
                    UserFile userFile = UserFile.builder()
                            .spaceType(fileSpace.getType())
                            .courseId(courseId)
                            .isDirectory(isDir.getStatus())
                            .fileName(userFileEditVo.getName())
                            .parentId(parent.getId())
                            .treeId(userFileService.getNextTreeId(parent))
                            .status(status)
                            .build();
                    newUserFile = userFile;
                }
                break;
            case STUDY_GROUP:
                Long studyGroupId = rootDirectory.getStudyGroupId();
                StudyGroup studyGroup = studyGroupDao.get(studyGroupId);
                if (studyGroup == null) {
                    throw new PermissionException();
                }
                if (!studyGroupUserDao.hasUser(studyGroup.getId(), WebContext.getUserId())
                        && !roleService.hasTeacherOrTutorRole()) {
                    throw new PermissionException();
                }

                if (isDir != Status.YES) {
                    FileInfo fileInfo = userFileService.getFileInfo(userFileEditVo.getFileId());
                    if (fileInfo == null) {
                        throw new BaseException("prop.value.not-exists", "fileId", String.valueOf(userFileEditVo.getFileId()));
                    }

                    //覆盖且重名，则替换原有文件
                    if(isCoveredBoolean && nameExist){
                        newUserFile = userFileService.coveredFilePackage(parentDirectoryId, userFileEditVo.getName(), fileInfo);
                    }else{
                        //不覆盖且重名，名字自动加后缀
                        if (nameExist && !isCoveredBoolean) {
                            String newName = userFileService.getNewFileName(fileName, fileInfo.getId(),  parentDirectoryId, isDirectoryInt);;
                            userFileEditVo.setName(newName);
                        }

                        //覆盖且不重名、不覆盖且不重名（大多数情况）
                        UserFile userFile = UserFile.builder()
                                .spaceType(fileSpace.getType())
                                .courseId(studyGroup.getCourseId())
                                .studyGroupId(studyGroupId)
                                .isDirectory(isDir.getStatus())
                                .fileName(userFileEditVo.getName())
                                .fileType(fileInfo.getFileType())
                                .fileSize(fileInfo.getFileSize())
                                .fileUrl(fileInfo.getFileId())
                                .parentId(parent.getId())
                                .treeId(userFileService.getNextTreeId(parent))
                                .status(status)
                                .build();
                        newUserFile = userFile;
                    }
                } else {
                    UserFile userFile = UserFile.builder()
                            .spaceType(fileSpace.getType())
                            .courseId(studyGroup.getCourseId())
                            .studyGroupId(studyGroupId)
                            .isDirectory(isDir.getStatus())
                            .fileName(userFileEditVo.getName())
                            .parentId(parent.getId())
                            .treeId(userFileService.getNextTreeId(parent))
                            .status(status)
                            .build();
                    newUserFile = userFile;
                }
                break;
            default:
                throw new PropValueUnRegistryException("spaceType", fileSpace);
        }

        if(nameExist && isCoveredBoolean){
            userFileDao.update(newUserFile);

            log.info("[UserFileDataEdit] cover file success, isDir={}, space={}, fileId={}, userId={}",
                    Status.statusOf(newUserFile.getIsDirectory()), fileSpace, newUserFile.getId(), WebContext.getUserId());
            return new LinkedInfo(String.valueOf(newUserFile.getId()), JSON.toJSONString(newUserFile));
        }else{
            userFileDao.save(newUserFile);

            log.info("[UserFileDataEdit] save file success, isDir={}, space={}, fileId={}, userId={}",
                    Status.statusOf(newUserFile.getIsDirectory()), fileSpace, newUserFile.getId(), WebContext.getUserId());
            return new LinkedInfo(String.valueOf(newUserFile.getId()), JSON.toJSONString(newUserFile));
        }
    }

    /**
     * @api {post} /userFile/modify 文件重命名
     * @apiDescription 用户文件、文件夹重命名
     * @apiName UserFileModify
     * @apiGroup Common
     *
     * @apiParam {Number} id ID
     * @apiParam {String} name 文件名称
     *
     * @apiExample {json} 请求示例:
     * {
     *     "courseId": 1,
     *     "isDirectory": 1,
     *     "fileName": "tmp",
     *     "parentId": 1
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 文件ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "1"
     * }
     *
     * @param dataEditInfo
     * @return
     */
    @ValidationParam(clazz = UserFileEditVo.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        UserFileEditVo userFileEditVo = JSON.parseObject(dataEditInfo.beanJson, UserFileEditVo.class);
        Long id = userFileEditVo.getId();
        Integer isDirectory = userFileEditVo.getIsDirectory();
        UserFile userFile = userFileDao.get(id);
        if (userFile == null) {
            throw new BaseException("prop.value.not-exists", "id", String.valueOf(userFileEditVo.getId()));
        }

        boolean nameExist = userFileDao.isNameExists(id, userFile.getParentId(), userFileEditVo.getName(), isDirectory);
        if(nameExist){
            throw new BaseException("file.name.exists");
        }

        FileSpaceTypeEnum fileSpace = FileSpaceTypeEnum.typeOf(userFile.getSpaceType());
        switch (fileSpace) {
            case PERSONAL:
                if (!Objects.equals(userFile.getUserId(), WebContext.getUserId())) {
                    log.info("[UserFileDataEdit] user can't rename file or dir on other's personal space, targetUserSpace={}, opUserId={}",
                            userFile.getUserId(), WebContext.getUserId());
                    throw new PermissionException();
                }

                break;
            case COURSE:
                Long courseId = userFile.getCourseId();
                if (!roleService.hasTeacherOrTutorRole()) {
                    log.info("[UserFileDataEdit] student can't rename file or dir on course's space, courseId={}, opUserId={}",
                            courseId, WebContext.getUserId());
                    throw new PermissionException();
                }

                break;
            case STUDY_GROUP:
                Long studyGroupId = userFile.getStudyGroupId();
                StudyGroup studyGroup = studyGroupDao.get(studyGroupId);
                if (studyGroup == null) {
                    throw new PermissionException();
                }
                if (!studyGroupUserDao.hasUser(studyGroup.getId(), WebContext.getUserId())
                        && !roleService.hasTeacherOrTutorRole()) {
                    throw new PermissionException();
                }

                break;
            default:
                throw new PropValueUnRegistryException("spaceType", fileSpace);
        }

        userFile.setFileName(userFileEditVo.getName());
        userFileDao.update(userFile);
        log.info("[UserFileDataEdit] rename file success, isDir={}, space={}, fileId={}, userId={}",
                Status.statusOf(userFile.getIsDirectory()), fileSpace, userFile.getId(), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(userFile.getId()), String.valueOf(userFile));
    }

    /**
     * @api {post} /userFile/deletes 文件删除
     * @apiDescription 删除文件或文件夹
     * @apiName UserFileDelete
     * @apiGroup Common
     *
     * @apiParam {String[]} ids ID
     *
     * @apiExample {json} 请求示例:
     *  [1, 2, 3]
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除文件ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "[1,2,3]"
     * }
     *
     * @param dataEditInfo
     * @return
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        List<UserFile> userFiles = userFileDao.gets(ids);
        for (UserFile userFile : userFiles) {
            if (Objects.equals(userFile.getIsSystemLevel(), Status.YES.getStatus())) {
                log.info("[UserFileDataEdit] can't move system level file or directory, sourceId={}, name={}, opUser={}",
                        userFile.getId(), userFile.getFileName(), WebContext.getUserId());
                throw new PermissionException();
            }

            moduleCompleteService.deleteModuleItemByOriginId(userFile.getId(), OriginTypeEnum.ASSIGNMENT.getType());
            FileSpaceTypeEnum fileSpace = FileSpaceTypeEnum.typeOf(userFile.getSpaceType());
            switch (fileSpace) {
                case PERSONAL:
                    if (!Objects.equals(userFile.getUserId(), WebContext.getUserId())) {
                        log.info("[UserFileDataEdit] delete file fail, fileSpace={}, owner={}, opUser={}",
                                fileSpace, userFile.getUserId(), WebContext.getUserId());
                        throw new PermissionException();
                    }

                    break;
                case COURSE:
                    Long courseId = userFile.getCourseId();
                    if (!roleService.hasTeacherOrTutorRole()) {
                        log.info("[UserFileDataEdit] delete file fail, user don't have teacher or tutor role, fileSpace={}, courseId={}, opUser={}",
                                fileSpace, courseId, WebContext.getUserId());
                        throw new PermissionException();
                    }

                    break;
                case STUDY_GROUP:
                    Long studyGroupId = userFile.getStudyGroupId();
                    StudyGroup studyGroup = studyGroupDao.get(studyGroupId);
                    if (studyGroup == null) {
                        log.info("[UserFileDataEdit] delete file fail, study group is not exists, fileSpace={}, studyGroup={}, opUser={}",
                                fileSpace, studyGroupId, WebContext.getUserId());
                        throw new PermissionException();
                    }
                    if (!studyGroupUserDao.hasUser(studyGroup.getId(), WebContext.getUserId())
                            && !roleService.hasTeacherOrTutorRole()) {
                        log.info("[UserFileDataEdit] delete file fail, user don't exists in study group, fileSpace={}, studyGroup={}, opUser={}",
                                fileSpace, studyGroupId, WebContext.getUserId());
                        throw new PermissionException();
                    }

                    break;
                default:
                    throw new PropValueUnRegistryException("spaceType", fileSpace);
            }

            userFileDao.deleteByTreeId(userFile.getTreeId());
            log.info("[UserFileDataEdit] delete file success, isDir={}, space={}, fileId={}, treeId={}, userId={}",
                    Status.statusOf(userFile.getIsDirectory()), fileSpace, userFile.getId(), userFile.getTreeId(), WebContext.getUserId());
        }

        List<Long> deletedIds = userFiles.stream().map(UserFile::getId).collect(Collectors.toList());
        log.info("[UserFileDataEdit] delete file finish, fileIds={}, opUser={}",
                JSON.toJSONString(deletedIds), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(deletedIds));
    }

}

package com.wdcloud.lms.business.file;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.vo.UserFileMoveVo;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileSpaceTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.TreeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文件复制：
 * 	   个人空间 ->　组空间
 * 	   个人空间 -> 课程空间（仅教师、助教）
 *     小组空间 -> 小组空间
 *     小组空间 -> 个人空间
 *     课程空间 -> 课程空间
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class UserFileCopyEdit implements ISelfDefinedEdit {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private RoleService roleService;

    /**
     * @api {post}/userFile/copy/edit 文件复制
     * @apiDescription 复制文件或文件夹到指定目录下，复制源与目标必须在不同空间内
     * @apiName UserFileCopy
     * @apiGroup Common
     *
     * @apiParam {Number} source 复制文件ID
     * @apiParam {Number} target 目标文件夹ID
     * @apiParam {String} [newName] 文件新的名字
     * @apiParam {Number=0,1} [isCovered] 是否覆盖原有文件
     *
     * @apiExample {json} 请求示例:
     * {
     *     "source": 11,
     *     "target": 1，
     *     "newName": "abc",
     *     "isCovered": 1,
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 复制后新文件（跟目录）ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserFileMoveVo fileCopyVo = JSON.parseObject(dataEditInfo.beanJson, UserFileMoveVo.class);
        String newName = fileCopyVo.getNewName();
        UserFile source = userFileDao.get(fileCopyVo.getSource());
        UserFile target = userFileDao.get(fileCopyVo.getTarget());
        if (source == null || target == null) {
            throw new BaseException("file.not-exist.error");
        }

        if(Objects.equals(source.getId(), target.getId())){
            throw new BaseException("file.path.same.error");
        }

        if (Objects.equals(target.getIsDirectory(), Status.NO.getStatus())) {
            log.info("[UserFileDataEdit] move file fail, target is not a directory, target={}, opUser={}", target.getId(), WebContext.getUserId());
            throw new BaseException("file.not-directory.error");
        }

        fileCopyPermissionValidate(source, target);

        UserFile newRootFile = null;

        String oldTreeId = source.getTreeId();
        String newTreeId = userFileDao.getLastTreeId(target.getId());
        String lastTreeId = userFileDao.getLastTreeId(target.getId());
        List<UserFile> copySources = userFileDao.findByTreeId(oldTreeId, null);

        String curTreeId;
        if (lastTreeId == null) {
            curTreeId = TreeIdUtils.produceTreeIdByParentId(target.getTreeId(), Constants.TREE_ID_PER_LEVEL_LENGTH);
        } else {
            curTreeId = TreeIdUtils.produceTreeId(lastTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH);
        }
        for (UserFile copySource : copySources) {
            if (Objects.equals(fileCopyVo.getSource(), copySource.getId())) {

                String fileName = StringUtil.isEmpty(newName) ? source.getFileName() : newName;
                boolean nameExist = userFileDao.isNameExists(source.getId(), target.getId(), fileName, source.getIsDirectory());

                Integer isCovered = fileCopyVo.getIsCovered();
                boolean isCoveredBoolean = false;
                if(null != isCovered && (isCovered.equals(Status.YES.getStatus()))){
                    isCoveredBoolean = true;
                }

                if(isCoveredBoolean && nameExist){
                    UserFile originUserFile = userFileDao.findByParentIdAndName(target.getId(), copySource.getFileName());;
                    userFileDao.delete(originUserFile.getId());
                }
                if (!isCoveredBoolean && nameExist) {
                    fileName = userFileService.getNewFileName(source.getFileName(), source.getId(), target.getId(), source.getIsDirectory());
                }
                copySource.setFileName(fileName);

                copySource.setTreeId(curTreeId);
                copySource.setParentId(target.getId());
                newRootFile = copySource;
            } else {
                copySource.setParentId(Constants.TREE_ROOT_PARENT_ID);
                copySource.setTreeId(copySource.getTreeId().replaceAll("^" + oldTreeId, newTreeId));
            }

            if (Objects.equals(target.getSpaceType(), FileSpaceTypeEnum.PERSONAL.getType())) {
                copySource.setUserId(target.getUserId());
            } else if (Objects.equals(target.getSpaceType(), FileSpaceTypeEnum.STUDY_GROUP.getType())) {
                copySource.setSpaceType(target.getSpaceType());
                copySource.setCourseId(target.getCourseId());
                copySource.setStudyGroupId(target.getStudyGroupId());
            } else if (Objects.equals(target.getSpaceType(), FileSpaceTypeEnum.COURSE.getType())) {
                copySource.setCourseId(target.getCourseId());
            }
            copySource.setUpdateTime(new Date());
            copySource.setStatus(Status.YES.getStatus());
            copySource.setId(null);
        }

        if (copySources.size() == 1) {
            userFileDao.insert(copySources.get(0));
        } else {
            userFileDao.batchInsert(copySources);
            userFileDao.reviseSubParentId(newTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH);
        }

        log.info("[UserFileCopyEdit] copy file success, targetDir={}, source oldId={}, newTreeId={}, isDirectory={}, copy number={}, opUserId={}",
                target.getId(), fileCopyVo.getSource(), newTreeId, source.getIsDirectory(), copySources.size(), WebContext.getUserId());

        return new LinkedInfo(String.valueOf(newRootFile.getId()));
    }

    private void fileCopyPermissionValidate(UserFile source, UserFile target) {
        FileSpaceTypeEnum sourceSpace = FileSpaceTypeEnum.typeOf(source.getSpaceType());
        FileSpaceTypeEnum targetSpace = FileSpaceTypeEnum.typeOf(target.getSpaceType());
        if (sourceSpace == targetSpace) {
            if (sourceSpace == FileSpaceTypeEnum.PERSONAL) {
                throw new PermissionException();
            }
            if (sourceSpace == FileSpaceTypeEnum.STUDY_GROUP && Objects.equals(source.getStudyGroupId(), target.getStudyGroupId())) {
                throw new PermissionException();
            }
            if (sourceSpace == FileSpaceTypeEnum.COURSE && Objects.equals(source.getCourseId(), target.getCourseId())) {
                throw new PermissionException();
            }
        } else if ((sourceSpace == FileSpaceTypeEnum.COURSE || targetSpace == FileSpaceTypeEnum.COURSE) && !roleService.hasTeacherOrTutorRole()) {
            throw new PermissionException();
        }

        userFileService.validateFileOperatePermission(source);
        userFileService.validateFileOperatePermission(target);
    }
}

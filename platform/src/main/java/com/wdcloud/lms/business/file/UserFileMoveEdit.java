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
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


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
 *
 * 文件移动：
 *      同一空间且同一根目录（同课程、同小组）
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class UserFileMoveEdit implements ISelfDefinedEdit {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    /**
     * @api {post} /userFile/move/edit 文件移动
     * @apiDescription 移动文件或文件夹；移动源与目标必须在同一空间内
     * @apiName UserFileMove
     * @apiGroup Common
     *
     * @apiParam {Number} source 移动文件ID
     * @apiParam {Number} target 目标文件夹ID（必须为文件夹）
     *
     * @apiExample {json} 请求示例:
     * {
     *     "source": 11,
     *     "target": 1
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 被移动文件ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @ValidationParam(clazz = UserFileMoveVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserFileMoveVo userFileMoveVo = JSON.parseObject(dataEditInfo.beanJson, UserFileMoveVo.class);
        UserFile source = userFileDao.get(userFileMoveVo.getSource());
        UserFile target = userFileDao.get(userFileMoveVo.getTarget());
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

        if (Objects.equals(source.getIsSystemLevel(), Status.YES.getStatus())) {
            log.info("[UserFileDataEdit] can't move system level file or directory, sourceId={}, name={}, opUser={}",
                    source.getId(), source.getFileName(), WebContext.getUserId());
            throw new PermissionException();
        }

        fileMovePermissionValidate(source, target);

        String newTreeId = userFileService.getNextTreeId(target);
        String oldTreeId = source.getTreeId();
        source.setTreeId(newTreeId);
        source.setParentId(target.getId());

        boolean nameExist = userFileDao.isNameExists(source.getId(), target.getId(), source.getFileName(), source.getIsDirectory());
        //文件名存在，则重命名，命名规则加-Copy
        if (nameExist) {
            String newName = userFileService.getNewFileName(source.getFileName(), source.getId(), target.getId(), source.getIsDirectory());
            source.setFileName(newName);
        }
        userFileDao.update(source);
        // 目录更新下级文件、文件夹treeId前缀
        if (Objects.equals(source.getIsDirectory(), Status.YES.getStatus())) {
            userFileDao.updateSubTreeId(oldTreeId, newTreeId, WebContext.getUserId());
        }

        log.info("[UserFileEdit] move file success, source={}, target={}, newTreeId={}, userId={}",
                source.getId(), target.getId(), newTreeId, WebContext.getUserId());
        return new LinkedInfo(String.valueOf(source.getId()));
    }


    private void fileMovePermissionValidate(UserFile source, UserFile target) {
        FileSpaceTypeEnum sourceSpace = FileSpaceTypeEnum.typeOf(source.getSpaceType());
        FileSpaceTypeEnum targetSpace = FileSpaceTypeEnum.typeOf(target.getSpaceType());
        if (sourceSpace != targetSpace) {
            throw new PermissionException();
        }

        if (sourceSpace == FileSpaceTypeEnum.PERSONAL) {
            if (!Objects.equals(source.getUserId(), target.getUserId())
                    || !Objects.equals(source.getUserId(), WebContext.getUserId())) {
                throw new PermissionException();
            }
        } else if (sourceSpace == FileSpaceTypeEnum.COURSE) {
            if (!Objects.equals(source.getCourseId(), target.getCourseId())
                    || !roleService.hasTeacherOrTutorRole()) {
                throw new PermissionException();
            }
        } else if (sourceSpace == FileSpaceTypeEnum.STUDY_GROUP) {
            if (!Objects.equals(source.getStudyGroupId(), target.getStudyGroupId())
                    || !studyGroupUserDao.hasUser(source.getStudyGroupId(), WebContext.getUserId())
                        && !roleService.hasTeacherOrTutorRole()) {
                throw new PermissionException();
            }
        }

    }
}

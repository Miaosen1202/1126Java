package com.wdcloud.lms.business.file;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.vo.UserFileVo;
import com.wdcloud.lms.config.UserFileProps;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileSpaceTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.StringUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_COURSE_FILE
)
public class UserFileDirOrImageQuery implements ISelfDefinedSearch<List<UserFileVo>> {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserFileProps userFileProps;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /userFile/courseFile/query 文件夹、图片查询
     * @apiDescription 查询课程空间或用户空间下所有文件夹、或所有图片文件；文件夹按treeId排序，图片按名称排序
     *
     * @apiName courseFileQuery
     * @apiGroup Common
     *
     * @apiParam {Number} [courseId] 课程ID，存在时查询课程空间，不存在时查询用户空间
     * @apiParam {Number=0,1} [queryImage] 1: 查询图片类型文件, 0: 查询文件夹
     * @apiParam {String} [name] 图片名称（查询图片时，可以指定图片名称过滤）
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 文件列表
     * @apiSuccess {Number} entity.id 文件ID
     * @apiSuccess {Number} entity.parentId 父目录ID
     * @apiSuccess {String} entity.fileName 文件名称
     * @apiSuccess {Number} [entity.fileSize] 文件大小
     * @apiSuccess {String} [entity.fileType] 文件类型
     * @apiSuccess {Number=0,1} entity.isDirectory 是否为目录
     * @apiSuccess {Number=0,1} entity.isSystemLevel 是否系统创建目录
     * @apiSuccess {Number=1,2,3} entity.spaceType 所属空间，1：课程 2：学习小组 3：个人
     * @apiSuccess {Number} [entity.userId] 所属用户ID
     * @apiSuccess {Number} [entity.courseId] 所属课程ID
     * @apiSuccess {Number} [entity.studyGroupId] 所属学习小组ID
     * @apiSuccess {Number=1,2,3} entity.status 发布状态 1:发布,2:未发布,3:限制访问
     * @apiSuccess {String} entity.treeId TreeId
     * @apiSuccess {Number} entity.createTime 创建时间
     * @apiSuccess {Number} entity.updateTime 最近修改时间
     * @apiSuccess {Number} entity.createUserId 创建用户ID
     * @apiSuccess {Number} entity.updateUserId 修改用户ID
     * @apiSuccess {String} [entity.createUserName] 创建用户名称
     * @apiSuccess {String} [entity.updateUserName] 修改用户名称
     *
     */
    @Override
    public List<UserFileVo> search(Map<String, String> condition) {
        UserFile rootDirectory;

        Example example = userFileDao.getExample();
        Example.Criteria criteria = example.createCriteria();


        if (condition.containsKey(Constants.PARAM_COURSE_ID)) {
            Long courseId = Long.parseLong(condition.get(Constants.PARAM_COURSE_ID));
            rootDirectory = userFileService.getCourseRootDirectory(courseId);
            if (rootDirectory == null) {
                throw new ParamErrorException();
            }

            String accessPublishedSQL = userFileService.accessPublishedSQL(rootDirectory);
            String accessUnpublishedSQL = userFileService.accessUnpublishedSQL(rootDirectory);
            String accessRestrictedSQL = userFileService.accessRestrictedSQL(rootDirectory);

            criteria.andEqualTo(UserFile.SPACE_TYPE, FileSpaceTypeEnum.COURSE.getType())
                    .andEqualTo(UserFile.COURSE_ID, courseId)
                    .andLike(UserFile.TREE_ID, rootDirectory.getTreeId() + "%")
                    .andCondition("(" + accessPublishedSQL + " or (" + accessUnpublishedSQL + ") or ("
                            + accessRestrictedSQL + "))");

        } else {
            Long userId = WebContext.getUserId();
            rootDirectory = userFileService.getUserRootDirectory(userId);
            if (rootDirectory == null) {
                throw new ParamErrorException();
            }

            criteria.andEqualTo(UserFile.SPACE_TYPE, FileSpaceTypeEnum.PERSONAL.getType())
                    .andEqualTo(UserFile.USER_ID, userId)
                    .andLike(UserFile.TREE_ID, rootDirectory.getTreeId() + "%");
        }

        if (condition.containsKey(Constants.PARAM_QUERY_IMAGE) &&
                Objects.equals(Integer.parseInt(condition.get(Constants.PARAM_QUERY_IMAGE)), Status.YES.getStatus())) {
            String name = condition.get(Constants.PARAM_NAME);
            if (StringUtil.isNotEmpty(name)) {
                criteria.andLike(UserFile.FILE_NAME, "%" + name + "%");
            }

            criteria.andIn(UserFile.FILE_TYPE, userFileProps.getEmbedContentSuffixes());
            example.orderBy(UserFile.FILE_NAME);
        } else {
            criteria.andEqualTo(UserFile.IS_DIRECTORY, Status.YES.getStatus());
            example.orderBy(UserFile.TREE_ID);
        }

        List<UserFile> userFiles = userFileDao.find(example);
        List<UserFileVo> result = BeanUtil.beanCopyPropertiesForList(userFiles, UserFileVo.class);
        Set<Long> createUserIds = result.stream()
                .map(UserFile::getCreateUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Long> updateUserIds = result.stream()
                .map(UserFile::getUpdateUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Long> userIds = new HashSet<>();
        userIds.addAll(createUserIds);
        userIds.addAll(updateUserIds);

        Map<Long, String> userIdNameMap = userDao.gets(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, User::getUsername));
        for (UserFileVo userFileVo : result) {
            userFileVo.setCreateUserName(userIdNameMap.get(userFileVo.getCreateUserId()));
            userFileVo.setUpdateUserName(userIdNameMap.get(userFileVo.getUpdateUserId()));
        }

        return result;
    }
}

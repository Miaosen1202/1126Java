package com.wdcloud.lms.business.file;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.vo.UserFileVo;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_BY_NAME
)
public class UserFileByNameQuery implements ISelfDefinedSearch<List<UserFileVo>> {
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /userFile/byName/query 文件检索
     * @apiDescription 文件检索：按名称检索指定根目录下文件
     * @apiName userFileQueryByName
     * @apiGroup Common
     *
     * @apiParam {String} name 名称
     * @apiParam {Number} rootId 根目录ID
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
     */
    @Override
    public List<UserFileVo> search(Map<String, String> condition) {
        if (!condition.containsKey(Constants.PARAM_NAME) || !condition.containsKey(Constants.PARAM_ROLE_ID)) {
            throw new ParamErrorException();
        }

        String name = condition.get(Constants.PARAM_NAME);
        if (StringUtil.isEmpty(name)) {
            throw new ParamErrorException();
        }

        long rootId = Long.parseLong(condition.get(Constants.PARAM_ROOT_ID));
        UserFile rootDir = userFileDao.get(rootId);
        if (rootDir == null) {
            throw new ParamErrorException();
        }

        userFileService.validateFileAccessPermission(rootDir);
        boolean canAccessUnpublished = userFileService.canAccessUnpublished(rootDir);

        Example example = userFileDao.getExample();
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo(Constants.PARAM_IS_DIRECTORY, Status.NO.getStatus())
                .andLike(Constants.PARAM_FULL_NAME, "%" + name + "%")
                .andLike(Constants.PARAM_TREE_ID, rootDir.getTreeId() + "%");

        //todo 目前文件查询，如果是学生要考虑是否有限制。改接口还未时候，暂不修改
        if (!canAccessUnpublished) {
            criteria.andEqualTo(Constants.PARAM_STATUS, Status.YES.getStatus());
        }

        example.orderBy(Constants.PARAM_FILE_NAME);
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

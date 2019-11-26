package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.studygroup.vo.StudyGroupUserVo;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP_USER)
public class StudyGroupUserQuery implements IDataQueryComponent<StudyGroupUser> {
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserDao userDao;

    /**
     * @api {post} /studyGroupUser/list 学习小组学生列表
     * @apiName studyGroupUserList
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} studyGroupId 学习小组
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 用户列表
     * @apiSuccess {Number} entity.studyGroupSetId 小组集ID
     * @apiSuccess {Number} entity.studyGroupId 小组ID
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} entity.username 用户登录名
     * @apiSuccess {String} [entity.nickname] 用户昵称
     * @apiSuccess {String} [entity.avatarFileUrl] 用户头像
     * @apiSuccess {Number=0,1} entity.isLeader 是否为组长
     *
     */
    @Override
    public List<? extends StudyGroupUser> list(Map<String, String> param) {
        if (!param.containsKey(Constants.PARAM_STUDY_GROUP_ID)) {
            throw new ParamErrorException();
        }

        long studyGroupId = Long.parseLong(param.get(Constants.PARAM_STUDY_GROUP_ID));
        List<StudyGroupUser> groupUsers = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupId(studyGroupId).build());

        List<StudyGroupUserVo> result = BeanUtil.beanCopyPropertiesForList(groupUsers, StudyGroupUserVo.class);
        if (ListUtils.isNotEmpty(groupUsers)) {
            List<Long> userIds = groupUsers.stream().map(StudyGroupUser::getUserId).collect(Collectors.toList());
            List<User> users = userDao.gets(userIds);
            Map<Long, User> userIdMap = users.stream().collect(Collectors.toMap(User::getId, a -> a));
            for (StudyGroupUserVo groupUserVo : result) {
                User user = userIdMap.get(groupUserVo.getUserId());
                if (user != null) {
                    groupUserVo.setUsername(user.getUsername());
                    groupUserVo.setNickname(user.getNickname());
                    groupUserVo.setAvatarFileUrl(user.getAvatarFileId());
                }
            }
        }
        return result;
    }
}

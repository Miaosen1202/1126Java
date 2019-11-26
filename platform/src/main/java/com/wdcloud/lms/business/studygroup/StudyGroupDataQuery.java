package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.Data;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP)
public class StudyGroupDataQuery implements IDataQueryComponent<StudyGroup> {
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private CourseDao courseDao;

    /**
     * @api {get} /studyGroup/list 学习小组列表
     * @apiName studyGroupList
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} [studyGroupSetId] 小组集ID，参数不存在时，返回课程下所有学习小组
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {Number} entity.maxMemberNumber 成员数量限制
     * @apiSuccess {Number} entity.joinType 加入方式, 1: 无限制 2: 仅限邀请
     * @apiSuccess {Number} entity.memberNumber 当前成员数量
     * @apiSuccess {Number} [entity.leaderId] 组长ID
     * @apiSuccess {Object[]} [entity.groupUsers] 小组成员
     * @apiSuccess {Number} entity.groupUsers.userId 用户ID
     * @apiSuccess {Number} entity.groupUsers.isLeader 是否为组长
     * @apiSuccess {String} entity.groupUsers.username 用户登录名
     * @apiSuccess {String} entity.groupUsers.nickname 用户昵称
     * @apiSuccess {Object} entity.studyGroupSet 所属组集
     * @apiSuccess {String} entity.studyGroupSet.name 组集名称
     * @apiSuccess {Number=0,1} entity.studyGroupSet.allowSelfSignup 允许学生自行注册
     * @apiSuccess {Number=0,1} entity.studyGroupSet.isSectionGroup 班级小组
     */
    @Override
    public List<? extends StudyGroup> list(Map<String, String> param) {
        if (!param.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));

        Long studyGroupSetId = null;
        if (param.containsKey(Constants.PARAM_STUDY_GROUP_SET_ID)) {
            studyGroupSetId = Long.parseLong(param.get(Constants.PARAM_STUDY_GROUP_SET_ID));
        }
        List<StudyGroup> studyGroups
                = studyGroupDao.find(StudyGroup.builder().courseId(courseId).studyGroupSetId(studyGroupSetId).build());

        List<StudyGroupVo> result = BeanUtil.beanCopyPropertiesForList(studyGroups, StudyGroupVo.class);
        result.forEach(o -> {
            if(o.getMaxMemberNumber() != null && o.getMaxMemberNumber() == Integer.MAX_VALUE){
                o.setMaxMemberNumber(null);
            }
        });

        if (ListUtils.isEmpty(result)) {
            return result;
        }

        List<Long> groupIds = studyGroups.stream().map(StudyGroup::getId).collect(Collectors.toList());

        Map<Long, List<StudyGroupUser>> groupMembers = studyGroupUserDao.findGroupMembers(groupIds);
        List<Long> allUserIds = groupMembers.values()
                .stream()
                .flatMap(a -> a.stream())
                .map(StudyGroupUser::getUserId)
                .collect(Collectors.toList());
        List<User> users = userDao.gets(allUserIds);
        Map<Long, User> userIdMap = users.stream().collect(Collectors.toMap(User::getId, a -> a));

        for (StudyGroupVo studyGroupVo : result) {
            studyGroupVo.setGroupUsers(new ArrayList<>());
            List<StudyGroupUser> studyGroupUsers = groupMembers.get(studyGroupVo.getId());

            if (studyGroupUsers == null) {
                continue;
            }
            List<StudyGroupUserVo> groupUserVos = BeanUtil.beanCopyPropertiesForList(studyGroupUsers, StudyGroupUserVo.class);
            for (StudyGroupUserVo groupUserVo : groupUserVos) {
                User user = userIdMap.get(groupUserVo.getUserId());
                groupUserVo.setNickname(user != null ? user.getNickname() : null);
                groupUserVo.setUsername(user != null ? user.getUsername() : null);
            }
            studyGroupVo.setMemberNumber(groupUserVos.size());
            studyGroupVo.getGroupUsers().addAll(groupUserVos);
        }

        Set<Long> groupSetIds = result.stream()
                .map(StudyGroupVo::getStudyGroupSetId)
                .collect(Collectors.toSet());

        Map<Long, StudyGroupSet> groupSetIdMap = studyGroupSetDao.gets(groupSetIds)
                .stream()
                .collect(Collectors.toMap(StudyGroupSet::getId, a -> a));
        for (StudyGroupVo studyGroupVo : result) {
            StudyGroupSet groupSet = groupSetIdMap.get(studyGroupVo.getStudyGroupSetId());
            studyGroupVo.setStudyGroupSet(groupSet);
        }

        return result;
    }

    /**
     * @api {get} /studyGroup/get 学习小组
     * @apiName studyGroupGet
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} id 小组ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 小组
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {Number} entity.maxMemberNumber 成员数量限制
     * @apiSuccess {Number} entity.joinType 加入方式, 1: 无限制 2: 仅限邀请
     * @apiSuccess {Number} entity.memberNumber 当前成员数量
     * @apiSuccess {Number} [entity.leaderId] 组长ID
     * @apiSuccess {Object} entity.course 所属课程
     * @apiSuccess {Object} entity.course.name 所属课程名称
     */
    @Override
    public StudyGroup find(String id) {
        long groupId = Long.parseLong(id);
        StudyGroup studyGroup = studyGroupDao.get(groupId);
        if (studyGroup == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_ID, id);
        }

        Course course = courseDao.get(studyGroup.getCourseId());
        StudyGroupWithCourseVo result = BeanUtil.beanCopyProperties(studyGroup, StudyGroupWithCourseVo.class);
        result.setCourse(course);
        return result;
    }

    @Data
    public static class StudyGroupWithCourseVo extends StudyGroup {
        private Course course;
    }

    @Data
    public static class StudyGroupVo extends StudyGroup {
        private Integer memberNumber;
        private List<StudyGroupUserVo> groupUsers;
        private StudyGroupSet studyGroupSet;
    }

    @Data
    public static class StudyGroupUserVo extends StudyGroupUser {
        private String username;
        private String nickname;
    }
}

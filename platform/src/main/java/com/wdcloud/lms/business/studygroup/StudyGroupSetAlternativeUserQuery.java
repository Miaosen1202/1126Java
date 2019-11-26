package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.studygroup.vo.StudyGroupUserVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程内未加入小组集下小组的用户查询
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_SET,
        functionName = Constants.FUNCTION_TYPE_ALTERNATIVE
)
public class StudyGroupSetAlternativeUserQuery implements ISelfDefinedSearch<List<StudyGroupUserVo>> {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserDao sectionUserDao;

    /**
     * @api {post} /studyGroupSet/alternative/query 学习小组待添加学生列表
     * @apiName studyGroupSetUserAlternativeSearch
     * @apiGroup StudyGroup
     * @apiParam {Number} studyGroupSetId 小组集
     * @apiParam {Number} [studyGroupId] 小组
     * @apiParam {String} [username] 登录名称过滤
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 用户列表
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} entity.username 用户登录名
     * @apiSuccess {String} [entity.nickname] 用户昵称
     */
    @Override
    public List<StudyGroupUserVo> search(Map<String, String> condition) {
        if (!condition.containsKey(Constants.PARAM_STUDY_GROUP_SET_ID)) {
            throw new ParamErrorException();
        }

        long studyGroupSetId = Long.parseLong(condition.get(Constants.PARAM_STUDY_GROUP_SET_ID));
        StudyGroupSet studyGroupSet = studyGroupSetDao.get(studyGroupSetId);

        Long studyGroupId = null;
        if (condition.containsKey(Constants.PARAM_STUDY_GROUP_ID)) {
            studyGroupId = Long.parseLong(condition.get(Constants.PARAM_STUDY_GROUP_ID));
        }

        Set<Long> allStudentIds = sectionUserDao.find(SectionUser.builder().courseId(studyGroupSet.getCourseId()).roleId(RoleEnum.STUDENT.getType()).build())
                .stream()
                .map(SectionUser::getUserId)
                .collect(Collectors.toSet());

        Set<Long> joinedGroupUserIdSet = new HashSet<>();
        // 学生可以同时存在学生学习小组集小的多个小组内；如果小组集是非学生小组集，则只能在一个小组集下小组内
        if (Objects.equals(studyGroupSet.getIsStudentGroup(), Status.NO.getStatus())) {
            List<StudyGroupUser> joinedGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupSetId(studyGroupSetId).build());
            joinedGroupUserIdSet = joinedGroupUsers.stream().map(StudyGroupUser::getUserId).collect(Collectors.toSet());
        } else if (studyGroupId != null) {
            List<StudyGroupUser> joinedGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                    .studyGroupSetId(studyGroupSetId)
                    .studyGroupId(studyGroupId)
                    .build());
            joinedGroupUserIdSet = joinedGroupUsers.stream().map(StudyGroupUser::getUserId).collect(Collectors.toSet());
        }

        List<User> users = userDao.findUsersByIdAndUsername(new ArrayList<>(allStudentIds),
                StringUtil.isNotEmpty(condition.get(Constants.PARAM_USER_NAME)) ? condition.get(Constants.PARAM_USER_NAME) : null);
        Map<Long, User> userIdMap = users.stream().collect(Collectors.toMap(User::getId, a -> a));

        List<StudyGroupUserVo> result = new ArrayList<>();

        for (User user : users) {
            Long userId = user.getId();
            if (!joinedGroupUserIdSet.contains(userId)) {
                StudyGroupUserVo studyGroupUserVo = new StudyGroupUserVo();
                studyGroupUserVo.setUserId(userId);
                studyGroupUserVo.setUsername(user.getUsername());
                studyGroupUserVo.setNickname(user.getNickname());
                result.add(studyGroupUserVo);
            }
        }

        return result;
    }
}

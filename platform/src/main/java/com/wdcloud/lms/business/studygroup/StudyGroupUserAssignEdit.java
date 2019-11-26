package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_USER,
        functionName = Constants.FUNCTION_TYPE_ASSIGN
)
public class StudyGroupUserAssignEdit implements ISelfDefinedEdit {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupDao studyGroupDao;

    /**
     * @api {post} /studyGroupUser/assign/edit 随机分配学生到小组
     * @apiDescription 分配学生，限制成员在相同班级时需要：1. 组为空且无成员数量限制 2. 待分配学生班级数量不超过组数量
     * @apiName StudyGroupUserAssignEdit
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} studyGroupSetId 小组集ID
     * @apiParam {Number} [isLimitMemberInSameSection] 限制组成员在相同班级
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {String} [entity] 小组集ID
     */
    @ValidationParam(clazz = RandomAssignGroupUserVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        RandomAssignGroupUserVo randomAssignGroupUserVo = JSON.parseObject(dataEditInfo.beanJson, RandomAssignGroupUserVo.class);
        StudyGroupSet studyGroupSet = studyGroupSetDao.get(randomAssignGroupUserVo.getStudyGroupSetId());
        if (studyGroupSet == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_STUDY_GROUP_SET_ID, randomAssignGroupUserVo.getStudyGroupSetId());
        }

        List<StudyGroup> studyGroups = studyGroupDao.find(StudyGroup.builder().studyGroupSetId(studyGroupSet.getId()).build());
        if (ListUtils.isEmpty(studyGroups)) {
            throw new BaseException("no.groups");
        }

        //用于判断是否达到小组最大人数
        List<Long> groupIds = studyGroups.stream().map(StudyGroup::getId).collect(Collectors.toList());
        Map<Long, List<StudyGroupUser>> groupMemberMap = studyGroupUserDao.findGroupMembers(groupIds);

        List<StudyGroupUser> groupUsers = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupSetId(studyGroupSet.getId()).build());
        List<SectionUser> studentSectionUsers = sectionUserDao.find(SectionUser.builder()
                .courseId(studyGroupSet.getCourseId())
                .roleId(RoleEnum.STUDENT.getType()).build());

        Set<Long> existsUserIds = new HashSet<>(groupUsers.stream().map(StudyGroupUser::getUserId).collect(Collectors.toSet()));
        // 循环分配
        int ind = 0;
        //标记达到最大人数的小组个数
        int groupToLimitCount = 0;
        int groupSize = studyGroups.size();
        outer:
        for (SectionUser studentSectionUser : studentSectionUsers) {
            Long userId = studentSectionUser.getUserId();
            if (!existsUserIds.contains(userId)) {
                StudyGroup group = studyGroups.get(ind++ % groupSize);
                Long groupId = group.getId();

                if(Objects.isNull(groupMemberMap.get(groupId))){
                    groupMemberMap.put(groupId, new ArrayList<StudyGroupUser>());
                }

                //小组人数已达到最大人数，取出一个未达到最大人数的小组，将人员加入。若小组到达到最大人数，则返回
                while(group.getMaxMemberNumber() == groupMemberMap.get(groupId).size()){
                    groupToLimitCount++;
                    if(groupToLimitCount == groupSize){
                        break outer;
                    }

                    group = studyGroups.get(ind++ % groupSize);
                    groupId = group.getId();
                }

                StudyGroupUser newGroupUser = StudyGroupUser.builder()
                        .courseId(group.getCourseId())
                        .studyGroupSetId(group.getStudyGroupSetId())
                        .studyGroupId(groupId)
                        .userId(userId)
                        .build();

                studyGroupUserDao.save(newGroupUser);
                existsUserIds.add(userId);
                groupMemberMap.get(groupId).add(newGroupUser);
            }
        }

        return new LinkedInfo(String.valueOf(randomAssignGroupUserVo.getStudyGroupSetId()));
    }

    @Data
    public static class RandomAssignGroupUserVo {
        @NotNull
        private Long studyGroupSetId;
        private Integer isLimitMemberInSameSection;
    }
}

package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.StudyGroupUserExtMapper;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.core.base.vo.StudyGroupMebmberNumberVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudyGroupUserDao extends CommonDao<StudyGroupUser, Long> {
    @Autowired
    private StudyGroupUserExtMapper studyGroupUserExtMapper;

    /**
     * 用户是否在某个组中
     *
     * @param groupId
     * @param userId
     * @return
     */
    public boolean hasUser(Long groupId, Long userId) {
        if (groupId == null || userId == null) {
            return false;
        }

        return count(StudyGroupUser.builder().studyGroupId(groupId).userId(userId).build()) > 0;
    }

    /**
     * 查找用户在小组集下的注册信息
     *
     * @param studyGroupSetId
     * @param userId
     * @return
     */
    public StudyGroupUser findJoined(Long studyGroupSetId, Long userId) {
        return findOne(StudyGroupUser.builder().studyGroupSetId(studyGroupSetId).userId(userId).build());
    }

    /**
     * 用户是否已添加到组集下的某个组中
     *
     * @param studyGroupSetId
     * @param userId
     * @return
     */
    public boolean hasAddToGroup(Long studyGroupSetId, Long userId) {
        if (studyGroupSetId == null || userId == null) {
            return false;
        }
        return count(StudyGroupUser.builder().studyGroupSetId(studyGroupSetId).userId(userId).build()) > 0;
    }

    public int getMaxGroupUserNumber(Long groupSetId) {
        List<StudyGroupUser> studyGroupUsers = find(StudyGroupUser.builder().studyGroupSetId(groupSetId).build());
        Map<Long, List<StudyGroupUser>> collect = studyGroupUsers.stream()
                .collect(Collectors.groupingBy(StudyGroupUser::getStudyGroupId));
        int max = 0;
        for (List<StudyGroupUser> value : collect.values()) {
            if (value.size() > max) {
                max = value.size();
            }
        }
        return max;
    }

    public void clearNoStudentUser(List<Long> courseIds) {
        if (ListUtils.isNotEmpty(courseIds)) {
            studyGroupUserExtMapper.clearNoStudentUser(courseIds);
        }
    }

    public void clearUserByGroupSets(List<Long> studyGroupSetIds) {
        if (ListUtils.isEmpty(studyGroupSetIds)) {
            return;
        }
        studyGroupUserExtMapper.clearUserByGroupSets(studyGroupSetIds);
    }

    /**
     * 设置或撤销用户小组长身份
     *
     * @param studyGroupId
     * @param userId
     */
    public void setLeader(Long studyGroupId, Long userId, Status isLeader) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(StudyGroupUser.STUDY_GROUP_ID, studyGroupId)
                .andEqualTo(StudyGroupUser.USER_ID, userId);
        mapper.updateByExampleSelective(StudyGroupUser.builder().isLeader(isLeader.getStatus()).build(), example);
    }

    public void cancelLeader(Long studyGroupId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(StudyGroupUser.STUDY_GROUP_ID, studyGroupId);
        mapper.updateByExampleSelective(StudyGroupUser.builder().isLeader(Status.NO.getStatus()).build(), example);
    }

    public Map<Long, List<StudyGroupUser>> findGroupMembers(List<Long> groupIds) {
        if (ListUtils.isEmpty(groupIds)) {
            return new HashMap<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(StudyGroupUser.STUDY_GROUP_ID, groupIds);
        List<StudyGroupUser> studyGroupUsers = find(example);
        return studyGroupUsers.stream()
                .collect(Collectors.groupingBy(StudyGroupUser::getStudyGroupId));
    }

    public int removeUser(Long courseId, Long userId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(StudyGroupUser.COURSE_ID, courseId)
                .andEqualTo(StudyGroupUser.USER_ID, userId);
        return delete(example);
    }

    public int deleteByCourseIds(Collection<Long> courseIds) {
        Example example = getExample();
        example.createCriteria()
                .andIn(StudyGroupUser.COURSE_ID, courseIds);
        return delete(example);
    }

    public List<StudyGroupUser> findGroupSetUsers(Collection<Long> groupSetIds) {
        if (groupSetIds == null || groupSetIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(StudyGroupUser.STUDY_GROUP_SET_ID, groupSetIds);
        return find(example);
    }

    public List<StudyGroupUser> findUserByCourseId(Long courseId) {
        return studyGroupUserExtMapper.findUserByCourseId(courseId);
    }

    /**
     * 功能：目前主要用于用户分配
     * @param courseId
     * @param studyGroupId
     * @return
     */
    public List<StudyGroupUser> findUserByCourseIdAndGroupId(Long courseId,Long studyGroupId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(StudyGroupUser.COURSE_ID, courseId)
                .andEqualTo(StudyGroupUser.STUDY_GROUP_ID, studyGroupId);
        return find(example);
    }

    @Override
    protected Class<StudyGroupUser> getBeanClass() {
        return StudyGroupUser.class;
    }


}
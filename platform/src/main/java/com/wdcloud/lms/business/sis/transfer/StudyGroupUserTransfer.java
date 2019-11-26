package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StudyGroupUserTransfer {
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private UserDao userDao;

    public List<StudyGroupUser> transfer(List<SisImportStudyGroupUser> importGroupUsers, Long opUserId, Org rootOrg) {
        delete(importGroupUsers, opUserId, rootOrg);
        return add(importGroupUsers, opUserId, rootOrg);
    }

    private List<StudyGroupUser> add(List<SisImportStudyGroupUser> importGroupUsers, Long opUserId, Org rootOrg) {
        List<SisImportStudyGroupUser> activeGroupUsers = importGroupUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeGroupUsers)) {
            return new ArrayList<>();
        }

        Set<String> groupSisIds = activeGroupUsers.stream()
                .map(SisImportStudyGroupUser::getGroupId)
                .collect(Collectors.toSet());
        Set<String> userSisIds = activeGroupUsers.stream()
                .map(SisImportStudyGroupUser::getUserId)
                .collect(Collectors.toSet());

        Map<String, StudyGroup> groupSisIdMap = studyGroupDao.findBySisIds(rootOrg.getTreeId(), groupSisIds)
                .stream()
                .collect(Collectors.toMap(StudyGroup::getSisId, a -> a));
        Map<String, User> userSisIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, a -> a));

        Map<Long, List<StudyGroupUser>> groupMembers =
                studyGroupUserDao.findGroupMembers(groupSisIdMap.values().stream().map(StudyGroup::getId).collect(Collectors.toList()));

        Map<Long, Map<Long, StudyGroupUser>> groupSetId2UserId2GroupUserMap = new HashMap<>();
        for (List<StudyGroupUser> groupUsers : groupMembers.values()) {
            for (StudyGroupUser groupUser : groupUsers) {
                if (!groupSetId2UserId2GroupUserMap.containsKey(groupUser.getStudyGroupSetId())) {
                    groupSetId2UserId2GroupUserMap.put(groupUser.getStudyGroupSetId(), new HashMap<>());
                }
                groupSetId2UserId2GroupUserMap.get(groupUser.getStudyGroupSetId()).put(groupUser.getUserId(), groupUser);
            }
        }

        List<StudyGroupUser> newGroupUsers = new ArrayList<>();
        for (SisImportStudyGroupUser activeGroupUser : activeGroupUsers) {
            StudyGroup studyGroup = groupSisIdMap.get(activeGroupUser.getGroupId());
            User user = userSisIdMap.get(activeGroupUser.getUserId());
            if (studyGroup == null || user == null) {
                continue;
            }
            Long studyGroupSetId = studyGroup.getStudyGroupSetId();
            if (groupSetId2UserId2GroupUserMap.containsKey(studyGroupSetId)
                    && groupSetId2UserId2GroupUserMap.get(studyGroupSetId).containsKey(user.getId())) {
                StudyGroupUser studyGroupUser = groupSetId2UserId2GroupUserMap.get(studyGroupSetId).get(user.getId());
                studyGroupUser.setStudyGroupId(studyGroup.getId());
                studyGroupUser.setUpdateUserId(opUserId);

                studyGroupUserDao.update(studyGroupUser);
            } else {
                StudyGroupUser groupUser = StudyGroupUser.builder()
                        .courseId(studyGroup.getCourseId())
                        .studyGroupSetId(studyGroupSetId)
                        .studyGroupId(studyGroup.getId())
                        .userId(user.getId())
                        .isLeader(Status.NO.getStatus())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                studyGroupUserDao.save(groupUser);
                newGroupUsers.add(groupUser);
            }
        }

        return newGroupUsers;
    }

    private void delete(List<SisImportStudyGroupUser> importGroupUsers, Long opUserId, Org rootOrg) {
        List<SisImportStudyGroupUser> deleteGroupUsers = importGroupUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());
        if (ListUtils.isEmpty(deleteGroupUsers)) {
            return;
        }

        Set<String> groupSisIds = deleteGroupUsers.stream()
                .map(SisImportStudyGroupUser::getGroupId)
                .collect(Collectors.toSet());
        Set<String> userSisIds = deleteGroupUsers.stream()
                .map(SisImportStudyGroupUser::getUserId)
                .collect(Collectors.toSet());


        Map<String, Long> groupSisIdToIdMap = studyGroupDao.findBySisIds(rootOrg.getTreeId(), groupSisIds)
                .stream()
                .collect(Collectors.toMap(StudyGroup::getSisId, StudyGroup::getId));
        Map<String, Long> userSisIdToIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, User::getId));

        for (SisImportStudyGroupUser deleteGroupUser : deleteGroupUsers) {
            Long groupId = groupSisIdToIdMap.get(deleteGroupUser.getGroupId());
            Long userId = userSisIdToIdMap.get(deleteGroupUser.getUserId());
            if (groupId != null && userId != null) {
                studyGroupUserDao.deleteByField(StudyGroupUser.builder().studyGroupId(groupId).userId(userId).build());
            }
        }
    }
}

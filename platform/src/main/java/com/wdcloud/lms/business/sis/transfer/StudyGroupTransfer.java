package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportStudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StudyGroupTransfer {
    @Autowired
    private StudyGroupSetService studyGroupSetService;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private UserFileService userFileService;

    public List<StudyGroup> transfer(List<SisImportStudyGroup> importStudyGroups, Long opUserId, Org rootOrg) {
        delete(importStudyGroups, opUserId, rootOrg);

        return add(importStudyGroups, opUserId, rootOrg);
    }

    public List<StudyGroup> add(List<SisImportStudyGroup> importStudyGroups, Long opUserId, Org rootOrg) {
        List<StudyGroup> newAddGroups = new ArrayList<>();

        List<SisImportStudyGroup> activeGroups = importStudyGroups.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());
        if (ListUtils.isEmpty(activeGroups)) {
            return newAddGroups;
        }

        List<String> groupSisIds = activeGroups.stream()
                .map(SisImportStudyGroup::getGroupId)
                .collect(Collectors.toList());

        Set<String> groupSetSisIds = activeGroups.stream()
                .map(SisImportStudyGroup::getGroupCategoryId)
                .collect(Collectors.toSet());

        Map<String, StudyGroupSet> groupSetSisIdMap = studyGroupSetDao.findBySisIds(rootOrg.getTreeId(), groupSetSisIds)
                .stream()
                .collect(Collectors.toMap(StudyGroupSet::getSisId, a -> a));

        Map<String, StudyGroup> existsGroupSisIdMap = studyGroupDao.findBySisIds(rootOrg.getTreeId(), groupSisIds)
                .stream()
                .collect(Collectors.toMap(StudyGroup::getSisId, a -> a));

        for (SisImportStudyGroup activeGroup : activeGroups) {
            StudyGroupSet studyGroupSet = groupSetSisIdMap.get(activeGroup.getGroupCategoryId());
            if (studyGroupSet == null) {
                continue;
            }

            StudyGroup existsGroup = existsGroupSisIdMap.get(activeGroup.getGroupId());
            if (existsGroup != null) {
                existsGroup.setName(activeGroup.getName());
                existsGroup.setUpdateUserId(opUserId);
                studyGroupDao.update(existsGroup);
            } else {
                StudyGroup newGroup = StudyGroup.builder()
                        .sisId(activeGroup.getGroupId())
                        .courseId(studyGroupSet.getCourseId())
                        .studyGroupSetId(studyGroupSet.getId())
                        .name(activeGroup.getName())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                studyGroupDao.save(newGroup);

                userFileService.initStudyGroupDir(newGroup.getCourseId(), newGroup.getId(), newGroup.getName());

                newAddGroups.add(newGroup);
            }
        }

        return newAddGroups;
    }

    public void delete(List<SisImportStudyGroup> importStudyGroups, Long opUserId, Org rootOrg) {
        List<SisImportStudyGroup> deleteGroups = importStudyGroups.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(deleteGroups)) {
            return;
        }

        List<String> groupSisIds = deleteGroups.stream()
                .map(SisImportStudyGroup::getGroupId)
                .collect(Collectors.toList());

        List<Long> groupIds = studyGroupDao.findBySisIds(rootOrg.getTreeId(), groupSisIds)
                .stream()
                .map(StudyGroup::getId)
                .collect(Collectors.toList());

        studyGroupSetService.deleteGroupByGroupIds(groupIds);
    }
}

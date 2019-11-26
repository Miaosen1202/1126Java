package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StudyGroupSetTransfer {
    @Autowired
    private StudyGroupSetService studyGroupSetService;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private StudyGroupDao studyGroupDao;

    public List<StudyGroupSet> transfer(List<SisImportStudyGroupSet> importStudyGroupSets, Long opUserId, Org rootOrg) {
        delete(importStudyGroupSets, opUserId, rootOrg);
        return add(importStudyGroupSets, opUserId, rootOrg);
    }

    private List<StudyGroupSet> add(List<SisImportStudyGroupSet> importStudyGroupSets, Long opUserId, Org rootOrg) {
        List<StudyGroupSet> newAddGroupSets = new ArrayList<>();

        List<SisImportStudyGroupSet> activeGroupSets = importStudyGroupSets.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeGroupSets)) {
            return newAddGroupSets;
        }

        Set<String> courseSisIds = activeGroupSets.stream()
                .map(SisImportStudyGroupSet::getCourseId)
                .collect(Collectors.toSet());
        List<String> groupSetSisIds = activeGroupSets.stream()
                .map(SisImportStudyGroupSet::getGroupCategoryId)
                .collect(Collectors.toList());

        Map<String, Course> courseSisIdMap = courseDao.findBySisIds(rootOrg.getTreeId(), courseSisIds)
                .stream()
                .collect(Collectors.toMap(Course::getSisId, a -> a));
        Map<String, StudyGroupSet> groupSetSisIdMap = studyGroupSetDao.findBySisIds(rootOrg.getTreeId(), groupSetSisIds)
                .stream()
                .collect(Collectors.toMap(StudyGroupSet::getSisId, a -> a));
        for (SisImportStudyGroupSet activeGroupSet : activeGroupSets) {
            if (groupSetSisIdMap.containsKey(activeGroupSet.getGroupCategoryId())) {
                StudyGroupSet exists = groupSetSisIdMap.get(activeGroupSet.getGroupCategoryId());
                exists.setName(activeGroupSet.getCategoryName());
                exists.setUpdateUserId(opUserId);
                studyGroupSetDao.update(exists);
            } else {
                if (courseSisIdMap.containsKey(activeGroupSet.getCourseId())) {
                    StudyGroupSet newGroupSet = StudyGroupSet.builder()
                            .sisId(activeGroupSet.getGroupCategoryId())
                            .courseId(courseSisIdMap.get(activeGroupSet.getCourseId()).getId())
                            .name(activeGroupSet.getCategoryName())
                            .leaderSetStrategy(LeaderSetStrategyEnum.MANUAL.getStrategy())
                            .isStudentGroup(Status.NO.getStatus())
                            .isSectionGroup(Status.NO.getStatus())
                            .createUserId(opUserId)
                            .updateUserId(opUserId)
                            .build();
                    studyGroupSetDao.save(newGroupSet);
                    newAddGroupSets.add(newGroupSet);
                }
            }
        }

        return newAddGroupSets;
    }

    private void delete(List<SisImportStudyGroupSet> importStudyGroupSets, Long opUserId, Org rootOrg) {
        List<SisImportStudyGroupSet> deleteGroupSets = importStudyGroupSets.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(deleteGroupSets)) {
            return;
        }

        List<String> groupSetSisIds = deleteGroupSets.stream()
                .map(SisImportStudyGroupSet::getGroupCategoryId)
                .collect(Collectors.toList());

        List<StudyGroupSet> groupSets = studyGroupSetDao.findBySisIds(rootOrg.getTreeId(), groupSetSisIds);
        List<Long> groupSetIds = groupSets.stream().map(StudyGroupSet::getId).collect(Collectors.toList());

        studyGroupSetService.deleteGroupSetByGroupSetIds(groupSetIds);
    }
}

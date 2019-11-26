package com.wdcloud.lms.business.sis.transfer;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SisImportSection;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SectionTransfer {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private CourseDao courseDao;

    public List<Section> transfer(List<SisImportSection> importSections, Long opUserId, Org rootOrg) {
        deleteSections(importSections, rootOrg);

        List<SisImportSection> activeImports = importSections.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeImports)) {
            return new ArrayList<>();
        }

        Set<String> courseSisIds = activeImports.stream()
                .map(SisImportSection::getCourseId)
                .collect(Collectors.toSet());
        Map<String, Long> courseSisIdToIdMap = courseDao.findBySisIds(rootOrg.getTreeId(), courseSisIds)
                .stream()
                .collect(Collectors.toMap(Course::getSisId, Course::getId));

        List<String> sectionSisIds = activeImports.stream()
                .map(SisImportSection::getSectionId)
                .collect(Collectors.toList());
        List<Section> existsSections = sectionDao.findBySisIds(rootOrg.getTreeId(), sectionSisIds);
        Map<String, Section> existsSectionSisIdMap = existsSections.stream()
                .collect(Collectors.toMap(Section::getSisId, a -> a));

        List<Section> newAddSections = new ArrayList<>();
        for (SisImportSection activeImport : activeImports) {
            Date startDate = parseDate(activeImport.getStartDate());
            Date endDate = parseDate(activeImport.getEndDate());

            if (existsSectionSisIdMap.containsKey(activeImport.getSectionId())) {
                Section section = existsSectionSisIdMap.get(activeImport.getSectionId());
                section.setName(activeImport.getName());
                section.setStartTime(startDate);
                section.setEndTime(endDate);
                section.setCreateUserId(opUserId);
                section.setUpdateUserId(opUserId);
                sectionDao.updateIncludeNull(section);
            } else {
                Section newSection = Section.builder()
                        .sisId(activeImport.getSectionId())
                        .courseId(courseSisIdToIdMap.get(activeImport.getCourseId()))
                        .name(activeImport.getName())
                        .startTime(startDate)
                        .endTime(endDate)
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                sectionDao.save(newSection);
                newAddSections.add(newSection);
            }
        }

        return newAddSections;
    }

    private Date parseDate(String date) {
        if (StringUtil.isNotEmpty(date)) {
            try {
                return StdDateFormat.getInstance().parse(date);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    private void deleteSections(List<SisImportSection> importSections, Org rootOrg) {
        // 删除
        List<SisImportSection> deleteImports = importSections.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(deleteImports)) {
            return;
        }

        List<String> deleteSectionSisIds = deleteImports.stream()
                .map(SisImportSection::getSectionId)
                .collect(Collectors.toList());
        List<Section> deleteSections = sectionDao.findBySisIds(rootOrg.getTreeId(), deleteSectionSisIds);
        List<Long> deleteSectionIds = deleteSections.stream().map(Section::getId).collect(Collectors.toList());

        sectionService.deleteBySectionIds(deleteSectionIds);

        Set<Long> courseIds = deleteSections.stream()
                .map(Section::getCourseId)
                .collect(Collectors.toSet());
        courseUserDao.deleteUnjoinSectionUsers(courseIds);
    }
}

package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.course.enums.CourseFormatEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.sis.enums.SisCourseFormatEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.MessageUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CourseTransfer {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private UserFileService userFileService;

    public List<Course> transfer(List<SisImportCourse> importCourses, Long opUserId, Org rootOrg) {
        List<SisImportCourse> deleteImportCourse = importCourses.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        // 删除
        List<String> courseSisIds = deleteImportCourse.stream().map(SisImportCourse::getCourseId).collect(Collectors.toList());
        if (ListUtils.isNotEmpty(courseSisIds)) {
            List<Course> courses = courseDao.findBySisIds(rootOrg.getTreeId(), courseSisIds);
            List<Long> courseIds = courses.stream()
                    .map(Course::getId)
                    .collect(Collectors.toList());
            if (ListUtils.isNotEmpty(courseIds)) {
                courseService.deleteCourseByIds(courseIds);
            }
        }

        // 新增
        List<SisImportCourse> activeImportCourses = importCourses.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeImportCourses)) {
            return new ArrayList<>();
        }

        Set<String> orgSisIds = activeImportCourses.stream()
                .map(SisImportCourse::getAccountId)
                .collect(Collectors.toSet());
        Map<String, Org> orgSisIdMap = orgDao.findBySisIds(orgSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(Org::getSisId, a -> a));

        Set<String> termSisIds = activeImportCourses.stream()
                .map(SisImportCourse::getTermId)
                .filter(StringUtil::isNotEmpty)
                .collect(Collectors.toSet());
        Map<String, Long> termIdToSisIdMap = termDao.findBySisIds(rootOrg.getTreeId(), termSisIds)
                .stream()
                .collect(Collectors.toMap(Term::getSisId, Term::getId));

        Term defaultTerm = termDao.findDefaultTerm(rootOrg.getId());

        List<String> activeCourseSisIds = activeImportCourses.stream()
                .map(SisImportCourse::getCourseId)
                .collect(Collectors.toList());

        List<Course> existsCourses = courseDao.findBySisIds(rootOrg.getTreeId(), activeCourseSisIds);
        Map<String, Course> existsCourseSisIdMap = existsCourses.stream()
                .collect(Collectors.toMap(Course::getSisId, a -> a));

        List<Course> newAddCourses = new ArrayList<>();
        for (SisImportCourse activeImportCourse : activeImportCourses) {
            if (existsCourseSisIdMap.containsKey(activeImportCourse.getCourseId())) {
                Long termId = termIdToSisIdMap.get(activeImportCourse.getTermId());
                if (termId == null) {
                    termId = defaultTerm.getId();
                }

                Course existsCourse = existsCourseSisIdMap.get(activeImportCourse.getCourseId());
                existsCourse.setName(activeImportCourse.getLongName());
                existsCourse.setUpdateUserId(opUserId);
                existsCourse.setTermId(termId);
                courseDao.update(existsCourse);
            } else {
                Long termId = termIdToSisIdMap.get(activeImportCourse.getTermId());
                if (termId == null) {
                    termId = defaultTerm.getId();
                }

                Org org = orgSisIdMap.get(activeImportCourse.getAccountId());
                if (org == null) {
                    org = rootOrg;
                }
                Course newCourse = Course.builder()
                        .orgId(org.getId())
                        .orgTreeId(org.getTreeId())
                        .sisId(activeImportCourse.getCourseId())
                        .name(activeImportCourse.getLongName())
                        .status(Status.NO.getStatus())
                        .termId(termId)
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                courseDao.save(newCourse);

                userFileService.initCourseSpaceDir(newCourse.getId(), newCourse.getName());

                sectionService.addDefaultSection(newCourse.getId(), newCourse.getName(), opUserId);

                studyGroupSetDao.addCourseDefaultStudyGroupSet(newCourse.getId(),
                        MessageUtil.getMessage("student-study-group-set-name"), opUserId);

                newAddCourses.add(newCourse);

                CourseFormatEnum formatEnum;
                SisCourseFormatEnum sisFormat = SisCourseFormatEnum.formatOf(activeImportCourse.getCourseFormat());
                if (sisFormat == null) {
                    sisFormat = SisCourseFormatEnum.ONLINE;
                }
                switch (sisFormat) {
                    case ONLINE:
                        formatEnum = CourseFormatEnum.ONLINE;
                        break;
                    case BLENDED:
                        formatEnum = CourseFormatEnum.BLENDED;
                        break;
                    case ON_CAMPUS:
                        formatEnum = CourseFormatEnum.ON_CAMPUS;
                        break;
                    default:
                        formatEnum = CourseFormatEnum.UNSET;
                        break;
                }
                CourseConfig courseConfig = CourseConfig.builder()
                        .courseId(newCourse.getId())
                        .format(formatEnum.getFormat())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                courseConfigDao.save(courseConfig);
            }
        }

        return newAddCourses;
    }
}

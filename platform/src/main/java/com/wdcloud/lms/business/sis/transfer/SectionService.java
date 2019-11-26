package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

@Component
public class SectionService {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private StudyGroupSetService studyGroupSetService;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    public int deleteBySectionIds(Collection<Long> sectionIds) {
        deleteSectionUserBySectionIds(sectionIds);
        return sectionDao.deletes(sectionIds);
    }

    public int deleteSectionByCourseIds(Collection<Long> courseIds) {
        deleteSectionUserByCourseIds(courseIds);

        courseUserDao.deleteByCourseIds(courseIds);
        return sectionDao.deleteByCourseIds(courseIds);
    }

    public int deleteSectionUserBySectionIds(Collection<Long> sectionIds) {
        return sectionUserService.deleteBySectionIds(sectionIds);
    }

    public int deleteSectionUserByCourseIds(Collection<Long> courseIds) {
        return sectionUserService.deleteByCourseIds(courseIds);
    }

    public void addDefaultSection(Long courseId, String name, Long opUserId) {
        Section defSection = Section.builder()
                .courseId(courseId)
                .isDefault(Status.YES.getStatus())
                .name(name)
                .createUserId(opUserId)
                .updateUserId(opUserId)
                .build();
        sectionDao.save(defSection);
    }
}

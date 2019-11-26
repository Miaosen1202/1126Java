package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.CourseConfigDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private StudyGroupSetService studyGroupSetService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private QuizDao quizDao;

    public int deleteCourseByIds(Collection<Long> ids) {
        courseConfigDao.deleteByCourseIds(ids);
        sectionService.deleteSectionByCourseIds(ids);
        studyGroupSetService.deleteGroupSetByCourseIds(ids);

        return courseDao.deletes(ids);
    }


}

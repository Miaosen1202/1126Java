package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.StudyGroupSetExtMapper;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class StudyGroupSetDao extends CommonDao<StudyGroupSet, Long> {
    @Autowired
    private StudyGroupSetExtMapper studyGroupSetExtMapper;

    public boolean isStudyGroupSetNameExists(Long courseId, String name) {
        return count(StudyGroupSet.builder().courseId(courseId).name(name).build()) > 0;
    }

    public List<StudyGroupSet> findBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        return studyGroupSetExtMapper.findBySisIds(rootOrgTreeId, sisIds);
    }

    public int deleteByCourseIds(Collection<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return 0;
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(StudyGroupSet.COURSE_ID, courseIds);
        return delete(example);
    }

    public void addCourseDefaultStudyGroupSet(Long courseId, String name, Long opUserId) {
        StudyGroupSet studyGroupSet = StudyGroupSet.builder()
                .courseId(courseId)
                .name(name)
                .isStudentGroup(Status.YES.getStatus())
                .allowSelfSignup(Status.YES.getStatus())
                .createUserId(opUserId)
                .updateUserId(opUserId)
                .build();
        save(studyGroupSet);
    }

    @Override
    protected Class<StudyGroupSet> getBeanClass() {
        return StudyGroupSet.class;
    }
}
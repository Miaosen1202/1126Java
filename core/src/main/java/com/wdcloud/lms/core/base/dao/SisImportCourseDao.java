package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportCourseExtMapper;
import com.wdcloud.lms.core.base.model.SisImportCourse;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportCourseDao extends CommonDao<SisImportCourse, Long> {
    @Autowired
    private SisImportCourseExtMapper sisImportCourseExtMapper;

    @Override
    protected Class<SisImportCourse> getBeanClass() {
        return SisImportCourse.class;
    }

    public List<SisImportCourse> findByCourseIds(Collection<String> courseIds, String treeId) {
        if (courseIds == null || courseIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportCourse.COURSE_ID, courseIds)
                .andLike(SisImportCourse.ORG_TREE_ID, treeId + "%");

        return find(example);
    }

    public void batchSaveOrUpdate(List<SisImportCourse> importCourses) {
        if (ListUtils.isNotEmpty(importCourses)) {
            sisImportCourseExtMapper.batchSaveOrUpdate(importCourses);
        }
    }
}
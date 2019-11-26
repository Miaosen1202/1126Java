package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.CourseConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

@Repository
public class CourseConfigDao extends CommonDao<CourseConfig, Long> {

    public CourseConfig getByCourseId(Long courseId) {
        return findOne(CourseConfig.builder().courseId(courseId).build());
    }

    public int updateByCourseId(CourseConfig courseConfig, Long courseId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseConfig.COURSE_ID, courseId);
        return mapper.updateByExampleSelective(courseConfig, example);
    }

    public void deleteByCourseIds(Collection<Long> courseIds) {
        Example example = getExample();
        example.createCriteria()
                .andIn(CourseConfig.COURSE_ID, courseIds);
        delete(example);
    }

    @Override
    protected Class<CourseConfig> getBeanClass() {
        return CourseConfig.class;
    }
}
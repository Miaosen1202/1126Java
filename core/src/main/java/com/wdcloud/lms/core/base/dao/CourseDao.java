package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.CourseExtMapper;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.vo.CourseFavoriteVo;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.lms.core.base.vo.CourseListVO;
import com.wdcloud.lms.core.base.vo.CoursePublicVo;
import com.wdcloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class CourseDao extends CommonDao<Course, Long> {
    @Autowired
    private CourseExtMapper courseExtMapper;

    public List<CourseFavoriteVo> findCourseFavorites(Map<String, Object> condition) {
        return courseExtMapper.findCourseFavorites(condition);
    }

    public List<CourseJoinedVo> findCourseJoined(Map<String, Object> condition) {
        return courseExtMapper.findCourseJoined(condition);
    }

    public int updateHomepageByCourseId(long courseId, String homepage) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(Course.ID, courseId);
        return mapper.updateByExampleSelective(Course.builder().homepage(homepage).build(), example);
    }

    public List<Course> findBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        return courseExtMapper.findBySisIds(rootOrgTreeId, sisIds);
    }

    public List<CourseListVO> getCourseByUsernameAndTermId(Map<String, Object> condition) {
        return courseExtMapper.getCourseByUsernameAndTermId(condition);
    }

    public List<CourseListVO> getCourseByNameAndTermId(Map<String, Object> condition) {
        return courseExtMapper.getCourseByNameAndTermId(condition);
    }

    public int conclude(Long id) {
        return update(Course.builder().id(id).isConcluded(Status.YES.getStatus()).concludedTime(DateUtil.now()).build());
    }

    public int unconclude(Long id) {
        return update(Course.builder().id(id).isConcluded(Status.NO.getStatus()).concludedTime(DateUtil.now()).build());
    }

    public List<CoursePublicVo> findPublicCourses(Map<String, Object> condition) {
        return courseExtMapper.findPublicCourses(condition);
    }

    @Override
    protected Class<Course> getBeanClass() {
        return Course.class;
    }
}
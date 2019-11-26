package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.vo.CourseFavoriteVo;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.lms.core.base.vo.CourseListVO;
import com.wdcloud.lms.core.base.vo.CoursePublicVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CourseExtMapper {
    /**
     * 查询喜欢的课程
     * @param condition 查询条件
     * @return 喜欢的课程集合VO对象
     */
    List<CourseFavoriteVo> findCourseFavorites(Map<String, Object> condition);

    /**
     * 查询已加入的课程
     * @param condition 查询条件
     * @return 加入的课程Vo对象集合
     */
    List<CourseJoinedVo> findCourseJoined(Map<String, Object> condition);

    /**
     * 查询当前机构及其子机构下，存在的课程sisId的课程
     * @param rootOrgTreeId 机构id
     * @param sisIds 课程sisid
     * @return 存在的课程
     */
    List<Course> findBySisIds(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);

    /**
     * 通过用户名和学期id查询课程
     * @param condition 查询条件
     * @return 课程信息集合
     */
    List<CourseListVO> getCourseByUsernameAndTermId(Map<String, Object> condition);

    /**
     * 根据k课程名字和学期id查找课程
     * @param condition
     * @return 课程信息集合
     */
    List<CourseListVO> getCourseByNameAndTermId(Map<String, Object> condition);

    /**
     * 根据条件（name、visibility、allowOpenRegistry）查询公共的课程
     * @param condition
     * @return List<CoursePublicVo>
     */
    List<CoursePublicVo> findPublicCourses(Map<String, Object> condition);
}

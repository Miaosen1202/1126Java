package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.vo.CourseUserDetailVo;
import com.wdcloud.lms.core.base.vo.UserCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CourseUserExtMapper {

    /**
     * 查找课程用户
     *
     * @param condition condition.courseId 课程ID
     *                  [condition.roleId]　过滤角色
     *                  [condition.name] 用户登录名/昵称过滤
     * @return List<CourseUserDetailVo>
     */
    List<CourseUserDetailVo> findCourseUserDetail(Map<String, Object> condition);

    /**
     * 根据条件（courseId、roleId）查询未加入课程的用户
     * @param courseId 课程id
     * @param roleId 角色id
     * @return List<CourseUser>
     */
    List<CourseUser> findNotPendingUsers(@Param("courseId") Long courseId, @Param("roleId") Long roleId);

    /**
     * 获取登录用户下的课程
     * @param userId 用户ID
     * @return List<UserCourseVo>
     */
    List<UserCourseVo> getUserCourseList(@Param("userId") Long userId);

    /**
     * 功能：查询具有某一课程权限的所有用户
     * @param courseId   课程Id
     * @return List<CourseUser>
     */
    List<CourseUser> findUserByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据条件（sisIds）查询该机构及其子机构下的课程用户
     * @param rootOrgTreeId
     * @param sisIds
     * @return List<CourseUser>
     */
    List<CourseUser> findBySisIds(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);

    /**
     *  删除未加入课程班级的人员
     * @param courseIds 课程id
     */
    void deleteUnjoinSectionUsers(@Param("courseIds") Collection<Long> courseIds);
}

package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionUserExtMapper {
    /**
     * 查询已加入某一课程的人员
     * @param courseId 课程id
     * @return List<User>
     */
    List<User> getUserByCourseId(@Param("courseId")Long courseId,@Param("roleId") Long roleId);

    /**
     * 功能：查询班级里某一课程Id下的所有用户
     * @param courseId  课程id
     * @return List<SectionUser>
     */
    List<SectionUser> findUserByCourseId(@Param("courseId") Long courseId);

    /**
     *  查询某一角色下的人员已加入的课程班级
     * @param courseId 课程id
     * @param userId 人员id
     * @param roleId 角色id
     * @return List<Section>
     */
    List<Section> findSectionsByThisStudentJoined(@Param("courseId")String courseId,@Param("userId") Long userId, @Param("roleId")Long roleId);

    /**
     *  查询某个课程下，某些班级下的所有学生角色的人员
     * @param courseId 课程id
     * @param sectionIds 班级ids
     * @return List<User>
     */
    List<User> getUserBySectionId(@Param("courseId") Long courseId,@Param("sectionIds") List<Long> sectionIds);
}

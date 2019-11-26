package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.core.base.vo.StudyGroupMebmberNumberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudyGroupUserExtMapper {

    /**
     * 将不在课程班级中的学生从学习小组-人员的中间表中删除
     * @param courseIds 课程id
     */
    void clearNoStudentUser(@Param("courseIds") List<Long> courseIds);

    /**
     * 根据查询条件（小组集id）,从小组-人员表中删除数据
     * @param studyGroupSetIds 小组集id
     */
    void clearUserByGroupSets(List<Long> studyGroupSetIds);

    /**
     * 根据查询条件（小组id），查询小组中的人员数量
     * @param groupIds 小组id
     * @return List<StudyGroupMebmberNumberVo>
     */
    List<StudyGroupMebmberNumberVo> findGroupMemberNumbers(List<Long> groupIds);

    /**
     * 功能：查询组里某一课程Id下的所有用户
     * @param courseId  课程id
     * @return  List<StudyGroupUser>
     */
    List<StudyGroupUser> findUserByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据查询条件（小组id），查询小组集中的人员数量
     * @param studyGroupSetId 小组集id
     * @param studyGroupId 小组id
     * @return 人员数量
     */
    Integer getStudyGroupUserNumber(@Param("studyGroupSetId") Long studyGroupSetId,
                                @Param("studyGroupId") Long studyGroupId);

    /**
     * 获取任务下的所有学生数量-组任务
     * @param studyGroupSetId 小组集id
     * @param courseId 课程id
     * @param roleId 角色id
     * @return Integer
     */
     Integer  getOriginStudyGroupUserCount(@Param("studyGroupSetId")Long studyGroupSetId,
                                           @Param("courseId")Long courseId,
                                           @Param("roleId")Long roleId);

}
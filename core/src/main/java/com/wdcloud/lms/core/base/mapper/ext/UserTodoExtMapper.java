package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.TodoVo;
import com.wdcloud.lms.core.base.vo.UserTodoVo;
import org.apache.ibatis.annotations.Param;
import com.wdcloud.lms.core.base.vo.GradeTodoVo;

import java.util.List;
import java.util.Map;

public interface UserTodoExtMapper {
    List<UserTodoVo> findList(Map<String, String> param);

    List<TodoVo> findToBeScoredListByCourseIds(@Param("courseIds")List<Long> courseIds);

    List<TodoVo> findUserTodoListByUserId(Map<String, Object> param);

    List<TodoVo> findToBeSubmitAssignmentByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findToBeSubmitDiscussionByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findToBeSubmitQuizByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<GradeTodoVo> findToBeSubmitAssignmentCountByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<GradeTodoVo> findToBeSubmitDiscussionCountByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<GradeTodoVo> findToBeSubmitQuizCountByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findUpcoimngAssignmentByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findUpcoimngDiscussionByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findUpcoimngQuizByCourseId(@Param("userId")Long userId, @Param("courseIds")List<Long> courseIds);

    List<TodoVo> findTeacherUpcoimngAssignmentByCourseId(@Param("userId")Long userId, @Param("courseId")Long courseId);

    List<TodoVo> findTeacherUpcoimngDiscussionByCourseId(@Param("userId")Long userId, @Param("courseId")Long courseId);

    List<TodoVo> findTeacherUpcoimngQuizByCourseId(@Param("userId")Long userId, @Param("courseId")Long courseId);

    List<TodoVo> findToBeScoredAssignmentListByCourseIds(@Param("courseIds")List<Long> courseIds);

    List<TodoVo> findToBeScoredDiscussListByCourseIds(@Param("courseIds")List<Long> courseIds);

    List<TodoVo> findToBeScoredQuizListByCourseIds(@Param("courseIds")List<Long> courseIds);
}

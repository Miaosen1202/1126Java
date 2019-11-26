package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserTodoExtMapper;
import com.wdcloud.lms.core.base.model.UserTodo;
import com.wdcloud.lms.core.base.vo.TodoVo;
import com.wdcloud.lms.core.base.vo.UserTodoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wdcloud.lms.core.base.vo.GradeTodoVo;

import java.util.List;
import java.util.Map;

@Repository
public class UserTodoDao extends CommonDao<UserTodo, Long> {
    @Autowired
    private UserTodoExtMapper userTodoExtMapper;

    @Override
    protected Class<UserTodo> getBeanClass() {
        return UserTodo.class;
    }

    public List<UserTodoVo> findList(Map<String, String> param) {
        return userTodoExtMapper.findList(param);
    }

    public List<TodoVo> findUserTodoListByUserId(Map<String, Object> param) {
        return userTodoExtMapper.findUserTodoListByUserId(param);
    }

    public List<TodoVo> findToBeSubmitAssignmentByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitAssignmentByCourseId(userId,courseIds);
    }

    public List<TodoVo> findToBeSubmitDiscussionByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitDiscussionByCourseId(userId,courseIds);
    }

    public List<TodoVo> findToBeSubmitQuizByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitQuizByCourseId(userId,courseIds);
    }

    public List<GradeTodoVo> findToBeSubmitAssignmentCountByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitAssignmentCountByCourseId(userId,courseIds);
    }

    public List<GradeTodoVo> findToBeSubmitDiscussionCountByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitDiscussionCountByCourseId(userId,courseIds);
    }

    public List<GradeTodoVo> findToBeSubmitQuizCountByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findToBeSubmitQuizCountByCourseId(userId,courseIds);
    }

    public List<TodoVo> findUpcoimngAssignmentByCourseId(Long userId, List<Long> courseIds) {
        return userTodoExtMapper.findUpcoimngAssignmentByCourseId(userId,courseIds);
    }

    public List<TodoVo> findUpcoimngDiscussionByCourseId(Long userId, List<Long>  courseIds) {
        return userTodoExtMapper.findUpcoimngDiscussionByCourseId(userId,courseIds);
    }

    public List<TodoVo> findUpcoimngQuizByCourseId(Long userId, List<Long>  courseIds) {
        return userTodoExtMapper.findUpcoimngQuizByCourseId(userId,courseIds);
    }

    public List<TodoVo> findTeacherUpcoimngAssignmentByCourseId(Long userId, Long courseId) {
        return userTodoExtMapper.findTeacherUpcoimngAssignmentByCourseId(userId,courseId);
    }

    public List<TodoVo> findTeacherUpcoimngDiscussionByCourseId(Long userId, Long courseId) {
        return userTodoExtMapper.findTeacherUpcoimngDiscussionByCourseId(userId,courseId);
    }

    public List<TodoVo> findTeacherUpcoimngQuizByCourseId(Long userId, Long courseId) {
        return userTodoExtMapper.findTeacherUpcoimngQuizByCourseId(userId,courseId);
    }

    public List<TodoVo> findToBeScoredAssignmentListByCourseIds(List<Long> courseIds) {
        return userTodoExtMapper.findToBeScoredAssignmentListByCourseIds(courseIds);
    }

    public List<TodoVo> findToBeScoredDiscussListByCourseIds(List<Long> courseIds) {
        return userTodoExtMapper.findToBeScoredDiscussListByCourseIds(courseIds);
    }

    public List<TodoVo> findToBeScoredQuizListByCourseIds(List<Long> courseIds) {
        return userTodoExtMapper.findToBeScoredQuizListByCourseIds(courseIds);
    }
}
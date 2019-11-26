package com.wdcloud.lms.business.calender;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CalendarUserCheckDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserTodoDao;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.vo.CalendarItemVo;
import com.wdcloud.lms.core.base.vo.TodoVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_UPCOMING)
public class UpComingDataQuery implements IDataQueryComponent<TodoVo> {
    
    @Autowired
    private UserTodoDao userTodoDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CarlendarService carlendarService;
    /**
     * @api {get} /upcoming/pageList 待办upcoming列表
     * @apiDescription upcoming列表
     * @apiName upcomingPageList
     * @apiGroup UserTodo
     *
     * @apiParam {Number} [courseId] 课程ID 课程首页必填，dashboard页为空
     * @apiExample {curl} 请求示例:
     * curl -i /upcoming/pageList?pageIndex=1&pageSize=5
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Number} entity.list 资源列表
     * @apiSuccess {Number} entity.list.dataType 数据类型 1:课程任务 2:日历待办
     * @apiSuccess {Number} entity.list.calendarType 日历类型, 1: 个人 2: 课程
     * @apiSuccess {Number} entity.list.doTime 日历待办执行时间
     * @apiSuccess {Number} entity.list.title 标题
     * @apiSuccess {Number} entity.list.userId 用户ID
     * @apiSuccess {Number} entity.list.courseId 课程Id
     * @apiSuccess {Number} entity.list.courseName 课程名称
     * @apiSuccess {Number} entity.list.originType 任务类型 1: 作业 2:讨论 3:测验
     * @apiSuccess {Number} entity.list.originId 任务ID
     * @apiSuccess {Number} entity.list.score 分值
     * @apiSuccess {Object} entity.list.assign 学生：我的分配 教师：最早的分配
     *
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总数
     */
    @Override
    public PageQueryResult<? extends TodoVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.putAll(param);
        params.put("userId", WebContext.getUserId());
        List<Long> courseIds =carlendarService.prepareCourseIds(params);

        List<TodoVo> todoVoList = null;
        List<TodoVo> taskList = new ArrayList<>();
        List<TodoVo> pageList = null;
        boolean isStudent = roleService.hasStudentRole();
        if (!isStudent) {//教师 查询所有课程 未到开始时间的计分任务列表
            if (ListUtils.isNotEmpty(courseIds)) {
                taskList= findTeacherUpCoimngListByCourseIds(courseIds);
            }
        }else{//学生 查询所有课程 未到开始时间的任务列表
            if (ListUtils.isNotEmpty(courseIds)) {
                taskList= findStudentUpCoimngListByCourseIds(courseIds);
            }
        }
        //日历mytodo 未来7到N天 按doTime升序
        params.put("startTime", DateUtils.addDays(new Date(),7));
        List<TodoVo> userTodoList=userTodoDao.findUserTodoListByUserId(params);
        //组合list 按doTime升序排列
        todoVoList=buildTodoList(taskList,userTodoList);
        //todoVoList 分页返回
        int offset = pageSize * (pageIndex - 1);
        pageList = todoVoList.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
        return new PageQueryResult<>(todoVoList.size(), pageList, pageSize, pageIndex);
    }

    private List<TodoVo> findTeacherUpCoimngListByCourseIds(List<Long> courseIds) {
        List<TodoVo> upCoimngList = new ArrayList<>();
        courseIds.forEach(courseId -> {
            //最早分配 未开始的作业列表
            List<TodoVo> assignmentList = userTodoDao.findTeacherUpcoimngAssignmentByCourseId(WebContext.getUserId(),courseId);
            //最早分配 未开始的讨论列表
            List<TodoVo> discussionList = userTodoDao.findTeacherUpcoimngDiscussionByCourseId(WebContext.getUserId(),courseId);
            //最早分配 未开始的测验列表
            List<TodoVo> quizList = userTodoDao.findTeacherUpcoimngQuizByCourseId(WebContext.getUserId(),courseId);
            upCoimngList.addAll(discussionList);
            upCoimngList.addAll(assignmentList);
            upCoimngList.addAll(quizList);
        });
        return upCoimngList;
    }




    /**
     *  未到开始时间，分配到该学生 的 任务列表
     *  1作业列表
     *  2讨论列表
     *  3测验列表
     * @param courseIds
     * @return
     */
    private List<TodoVo> findStudentUpCoimngListByCourseIds(List<Long> courseIds) {
        List<TodoVo> upCoimngList = new ArrayList<>();
        //分配到我并且未开始的作业列表
        List<TodoVo> assignmentList = userTodoDao.findUpcoimngAssignmentByCourseId(WebContext.getUserId(),courseIds);
        //分给到我并且未开始的讨论列表
        List<TodoVo> discussionList = userTodoDao.findUpcoimngDiscussionByCourseId(WebContext.getUserId(),courseIds);
        //分给到我并且未开始的测验列表
        List<TodoVo> quizList = userTodoDao.findUpcoimngQuizByCourseId(WebContext.getUserId(),courseIds);
        upCoimngList.addAll(discussionList);
        upCoimngList.addAll(assignmentList);
        upCoimngList.addAll(quizList);
        return upCoimngList;
    }

    private  List<TodoVo> buildTodoList(List<TodoVo> taskList, List<TodoVo> userTodoList) {
        List<TodoVo> all = new ArrayList<>();
        all.addAll(taskList);
        all.addAll(userTodoList);
        List<TodoVo> result = all.stream()
                .sorted(Comparator.comparing(TodoVo::getDoTime,Comparator.nullsLast(Date::compareTo)))
                .collect(Collectors.toList());
        return result;
    }

}

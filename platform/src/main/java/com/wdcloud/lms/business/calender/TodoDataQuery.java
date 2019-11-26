package com.wdcloud.lms.business.calender;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CalendarUserCheckDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserTodoDao;
import com.wdcloud.lms.core.base.enums.Status;
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
@ResourceInfo(name = Constants.RESOURCE_TYPE_TODO)
public class TodoDataQuery implements IDataQueryComponent<TodoVo> {
    
    @Autowired
    private UserTodoDao userTodoDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CarlendarService carlendarService;
    /**
     * @api {get} /todo/pageList 待办todo列表
     * @apiDescription TODO列表
     * @apiName todoPageList
     * @apiGroup UserTodo
     *
     * @apiParam {Number} [courseId] 课程ID 课程首页必填，dashboard页为空
     * @apiExample {curl} 请求示例:
     * curl -i /todo/pageList?pageIndex=1&pageSize=5
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Number} entity.list 资源列表
     * @apiSuccess {Number} entity.list.dataType 数据类型 1:待打分任务 2:日历待办 3待提交任务
     * @apiSuccess {Number} entity.list.calendarType 日历类型, 1: 个人 2: 课程
     * @apiSuccess {Number} entity.list.doTime 日历待办执行时间
     * @apiSuccess {Number} entity.list.title 标题
     * @apiSuccess {Number} entity.list.userId 用户ID
     * @apiSuccess {Number} entity.list.courseId 课程Id
     * @apiSuccess {Number} entity.list.courseName 课程名称
     * @apiSuccess {Number} entity.list.originType 任务类型 1: 作业 2:讨论 3:测验
     * @apiSuccess {Number} entity.list.originId 任务ID
     * @apiSuccess {Number} entity.list.score 分值
     * @apiSuccess {Number} entity.list.toBeScoredTotal 待打分数量
     * @apiSuccess {Number} entity.list.dueOrClose 0:显示dueTime 1:显示closeTime
     * @apiSuccess {Number} entity.list.limitTime 截止时间dueTime
     * @apiSuccess {Number} entity.list.endTime 结束时间closeTime
     * @apiSuccess {Number} entity.list.isLated 是否迟交 1:是 0：否
     *
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总页数
     */
    @Override
    public PageQueryResult<? extends TodoVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = Maps.newHashMap();
        params.putAll(param);
        params.put("userId", WebContext.getUserId());
        params.put("roleId", WebContext.getRoleId());
        List<Long> courseIds =carlendarService.prepareCourseIds(params);
        List<TodoVo> todoVoList = new ArrayList<>();
        List<TodoVo> taskList = new ArrayList<>();
        List<TodoVo> pageList = new ArrayList<>();
        boolean isTeacher =roleService.hasTeacherOrTutorRole();
        if (isTeacher) {//教师 查询所有课程的任务待打分列表 按打分数量排序
            if (ListUtils.isNotEmpty(courseIds)) {
                taskList=findToBeScoredListByCourseIds(courseIds);
            }
        }else{//学生 查询所有课程的任务待提交列表 按排序依据排序
            if (ListUtils.isNotEmpty(courseIds)) {
                taskList=findToBeSubmitListByCourseIds(courseIds);
            }
        }
        //日历mytodo 0到7天 按doTime升序
        params.put("startTime", new Date());
        params.put("endTime", DateUtils.addDays(new Date(),7));
        List<TodoVo> userTodoList=userTodoDao.findUserTodoListByUserId(params);
        //3+2 组合list
        todoVoList=buildTodoList(taskList,userTodoList);
        //todoVoList 分页返回
        pageList = todoVoList.stream().skip(pageSize * (pageIndex - 1)).limit(pageSize).collect(Collectors.toList());
        return new PageQueryResult<>(todoVoList.size(), pageList, pageSize, pageIndex);
    }

    private List<TodoVo> findToBeScoredListByCourseIds(List<Long> courseIds) {
        List<TodoVo> list = new ArrayList<>();
        List<TodoVo> assignmentList=userTodoDao.findToBeScoredAssignmentListByCourseIds(courseIds);
        List<TodoVo> discussList=userTodoDao.findToBeScoredDiscussListByCourseIds(courseIds);
        List<TodoVo> quizList=userTodoDao.findToBeScoredQuizListByCourseIds(courseIds);
        list.addAll(assignmentList);
        list.addAll(discussList);
        list.addAll(quizList);
        return list.stream().sorted(Comparator.comparing(TodoVo::getToBeScoredTotal).reversed()).collect(Collectors.toList());
    }


    /**
     *  任务待提交列表
     *  1作业待提交列表
     *  2讨论待提交列表
     *  3测验待提交列表
     *  排序依据
     * @param courseIds
     * @return
     */
    private List<TodoVo> findToBeSubmitListByCourseIds(List<Long> courseIds) {
        List<TodoVo> toBeSubmitList = new ArrayList<>();
        //分配到我并且已经开始的作业列表
        List<TodoVo> assignmentList = userTodoDao.findToBeSubmitAssignmentByCourseId(WebContext.getUserId(),courseIds);
        //分给到我并且已经开始的讨论列表
        List<TodoVo> discussionList = userTodoDao.findToBeSubmitDiscussionByCourseId(WebContext.getUserId(),courseIds);
        //分给到我并且已经开始的测验列表
        List<TodoVo> quizList = userTodoDao.findToBeSubmitQuizByCourseId(WebContext.getUserId(),courseIds);
        toBeSubmitList.addAll(discussionList);
        toBeSubmitList.addAll(assignmentList);
        toBeSubmitList.addAll(quizList);
        /**
         * 寻找排序依据 规则比较复杂
         * due，close都不空  current < due 依据为 due   due < current  依据为 close
         * due空 close不空   依据为 close
         * due不空 close空   依据为 due
         * due，close都为空  排在最后
         */
        List<TodoVo> voList = toBeSubmitList;
        voList.forEach(todoVo -> {
            todoVo.setIsLated(Long.valueOf(Status.NO.getStatus()));
            todoVo.setDueOrClose(Long.valueOf(Status.NO.getStatus()));
            if (todoVo.getLimitTime()!=null&&todoVo.getEndTime()!=null) {
                if (new Date().before(todoVo.getLimitTime())) {
                    todoVo.setOrderByTime(todoVo.getLimitTime());
                }else{
                    todoVo.setOrderByTime(todoVo.getEndTime());
                    todoVo.setDueOrClose(Long.valueOf(Status.YES.getStatus()));
                    todoVo.setIsLated(Long.valueOf(Status.YES.getStatus()));
                }
            }else if(todoVo.getLimitTime()==null&&todoVo.getEndTime()!=null){
                todoVo.setOrderByTime(todoVo.getEndTime());
                todoVo.setDueOrClose(Long.valueOf(Status.YES.getStatus()));
            }else if(todoVo.getLimitTime()!=null&&todoVo.getEndTime()==null){
                todoVo.setOrderByTime(todoVo.getLimitTime());
                if (new Date().after(todoVo.getLimitTime())) {
                    todoVo.setDueOrClose(Long.valueOf(Status.YES.getStatus()));
                    todoVo.setIsLated(Long.valueOf(Status.YES.getStatus()));
                }
            }
        });
        //按排序依据升序排列
        List<TodoVo> result=voList.stream()
                .sorted(Comparator.comparing(TodoVo::getOrderByTime,Comparator.nullsLast(Date::compareTo)))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 3+2 组装成新list
     * @param toBeScoredList
     * @param userTodoList
     * @return
     */
    private  List<TodoVo> buildTodoList(List<TodoVo> toBeScoredList, List<TodoVo> userTodoList) {
        List<TodoVo> a = toBeScoredList;
        List<TodoVo> b = userTodoList;
        if (ListUtils.isEmpty(a)) {
            return b;
        }
        if (ListUtils.isEmpty(b)) {
            return a;
        }
        for(int i=0;i+3<a.size();){
            i+=3;
            if (b!=null&&b.size()>0) {
                a.add(i,b.get(0));
                b.remove(0);
                i+=1;
                if (b != null&&b.size()>0) {
                    a.add(i,b.get(0));
                    b.remove(0);
                    i+=1;
                }
            }else{
                break;
            }

        }
        a.addAll(b);
        return a;
    }

}

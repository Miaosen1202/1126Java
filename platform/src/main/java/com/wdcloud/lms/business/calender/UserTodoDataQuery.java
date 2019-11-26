package com.wdcloud.lms.business.calender;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.UserTodoDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.vo.UserTodoVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_TODO)
public class UserTodoDataQuery implements IDataQueryComponent<UserTodoVo> {
    
    @Autowired
    private UserTodoDao userTodoDao;
    @Autowired
    private CarlendarService carlendarService;
    @Autowired
    private RoleService roleService;
    /**
     * @api {get} /calendarTodo/list 日历待办列表
     * @apiName calendarTodoList
     * @apiGroup Calendar
     *
     * @apiParam {Number} [startDate] 开始日期 10位单位秒
     * @apiParam {Number} [endDate] 结束日期 10位单位秒
     * @apiParam {String} contextCodes 日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 ) 如 1_123,2_123
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 列表
     * @apiSuccess {Number} entity.id 待办ID
     * @apiSuccess {Number=1,2,3} entity.calendarType 项目类型 1: 个人 2: 课程
     * @apiSuccess {Number} entity.userId 所属用户ID
     * @apiSuccess {String} entity.userName 所属用户名称
     * @apiSuccess {Number} entity.courseId 所属课程ID(calendarType=2,3时非空)
     * @apiSuccess {String} entity.courseName 所属课程名称
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.content 内容
     * @apiSuccess {String} entity.doTime 执行时间
     */
    @Override
    public List<? extends UserTodoVo> list(Map<String, String> param) {
        param.put("userId", WebContext.getUserId()+"");
        List<UserTodoVo> todoList = new ArrayList<>();
        if (StringUtils.isBlank(param.get("contextCodes"))) {
            return todoList;
        }
        //分两类（个人 课程）  查询列表
        Map<String, List<String>> calendarTypeIdsMap=carlendarService.buildCalendarItemParam(param.get("contextCodes"));
        //个人
        if (calendarTypeIdsMap.get(CalendarTypeEnum.PERSON.getValue().toString())!=null) {
            param.put("calendarType",CalendarTypeEnum.PERSON.getValue().toString());
            param.put("userId", StringUtils.join(calendarTypeIdsMap.get(CalendarTypeEnum.PERSON.getValue().toString()),","));
            List<UserTodoVo> list=userTodoDao.findList(param);
            todoList.addAll(list);
        }
        //课程
        if (roleService.hasStudentRole()&&calendarTypeIdsMap.get(CalendarTypeEnum.COURSE.getValue().toString())!=null) {
            param.put("calendarType",CalendarTypeEnum.COURSE.getValue().toString());
            param.put("courseIds", StringUtils.join(calendarTypeIdsMap.get(CalendarTypeEnum.COURSE.getValue().toString()),","));
            List<UserTodoVo> list=userTodoDao.findList(param);
            todoList.addAll(list);
        }
        return todoList;
    }



}

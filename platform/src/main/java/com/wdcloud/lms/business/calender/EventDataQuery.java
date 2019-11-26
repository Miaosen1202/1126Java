package com.wdcloud.lms.business.calender;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.EventDao;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.EventVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_EVENT)
public class EventDataQuery implements IDataQueryComponent<EventVo> {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private CarlendarService carlendarService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    /**
     * @api {get} /calendarEvent/list 日历事件列表
     * @apiName calendarEventList
     * @apiGroup Calendar
     *
     * @apiParam {Number} [startDate] 开始日期 10位单位秒
     * @apiParam {Number} [endDate] 结束日期 10位单位秒
     * @apiParam {String} contextCodes 日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 列表
     * @apiSuccess {Number} entity.id 事件ID
     * @apiSuccess {Number=1,2,3} entity.calendarType 项目类型 1: 个人 2: 课程 3: 学习小组
     * @apiSuccess {Number} entity.userId 所属用户id
     * @apiSuccess {Number} entity.userName 所属用户名称
     * @apiSuccess {Number} entity.courseId 所属课程(calendarType=2,3时非空)
     * @apiSuccess {Number} entity.courseName 所属课程名称
     * @apiSuccess {Number} entity.studyGroupId 所属小组(calendarType=3时非空)
     * @apiSuccess {Number} entity.studyGroupName 所属小组名称
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.description 描述
     * @apiSuccess {String} entity.location 位置
     * @apiSuccess {String} entity.address 地址
     * @apiSuccess {String} entity.startTime 开始时间
     * @apiSuccess {String} entity.endTime 结束时间
     */
    @Override
    public List<? extends EventVo> list(Map<String, String> param) {
        List<EventVo> eventList = new ArrayList<>();
        if (StringUtils.isBlank(param.get("contextCodes"))) {
            return eventList;
        }
        param.put("userId", WebContext.getUserId()+"");

        Map<String, List<String>> calendarTypeIdsMap=carlendarService.buildCalendarItemParam(param.get("contextCodes"));
        //个人
        if (calendarTypeIdsMap.get(CalendarTypeEnum.PERSON.getValue().toString())!=null) {
            param.put("calendarType",CalendarTypeEnum.PERSON.getValue().toString());
            param.put("userId", WebContext.getUserId()+"");
            List<EventVo> list=eventDao.findEventList(param);
            eventList.addAll(list);
        }
        //课程
        if (calendarTypeIdsMap.get(CalendarTypeEnum.COURSE.getValue().toString())!=null) {
            param.put("calendarType",CalendarTypeEnum.COURSE.getValue().toString());
            param.put("courseIds", StringUtils.join(calendarTypeIdsMap.get(CalendarTypeEnum.COURSE.getValue().toString()),","));
            List<EventVo> list=eventDao.findEventList(param);
            eventList.addAll(list);
        }
        //小组
        if (calendarTypeIdsMap.get(CalendarTypeEnum.STYDYGROUP.getValue().toString())!=null) {
            param.put("calendarType",CalendarTypeEnum.STYDYGROUP.getValue().toString());
            param.put("studyGroupIds", StringUtils.join(calendarTypeIdsMap.get(CalendarTypeEnum.STYDYGROUP.getValue().toString()),","));
            List<EventVo> list=eventDao.findEventList(param);
            eventList.addAll(list);
        }
        return eventList;
    }

    /**
     * @api {get} /calendarEvent/get 日历事件详情
     * @apiName calendarEventGet
     * @apiGroup Calendar
     *
     * @apiParam {Number} data 事件ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {Number} entity.id 事件ID
     * @apiSuccess {Number=1,2,3} entity.calendarType 项目类型 1: 个人 2: 课程 3: 学习小组
     * @apiSuccess {Number} entity.userId 所属用户id
     * @apiSuccess {Number} entity.userName 所属用户名称
     * @apiSuccess {Number} entity.courseId 所属课程(calendarType=2,3时非空)
     * @apiSuccess {Number} entity.courseName 所属课程名称
     * @apiSuccess {Number} entity.studyGroupId 所属小组(calendarType=3时非空)
     * @apiSuccess {Number} entity.studyGroupName 所属小组名称
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.description 描述
     * @apiSuccess {String} entity.location 位置
     * @apiSuccess {String} entity.address 地址
     * @apiSuccess {String} entity.startTime 开始时间
     * @apiSuccess {String} entity.endTime 结束时间
     */
    public EventVo find(String id) {
         Event event=eventDao.findOne(Event.builder().id(Long.valueOf(id)).build());
         EventVo eventVo  = BeanUtil.beanCopyProperties(event, EventVo.class);
         User user=userDao.findOne(User.builder().id(event.getUserId()).build());
         eventVo.setUserName(user.getNickname());
         if (eventVo.getCalendarType().equals(CalendarTypeEnum.COURSE.getValue())) {
             Course course=courseDao.findOne(Course.builder().id(event.getCourseId()).build());
             eventVo.setCourseName(course.getName());
         }else if(eventVo.getCalendarType().equals(CalendarTypeEnum.STYDYGROUP.getValue())) {
             StudyGroup studyGroup = studyGroupDao.findOne(StudyGroup.builder().id(event.getStudyGroupId()).build());
             eventVo.setStudyGroupName(studyGroup.getName());
         }
         return eventVo;
    }

}

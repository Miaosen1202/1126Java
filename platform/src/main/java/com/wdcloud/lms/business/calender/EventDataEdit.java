package com.wdcloud.lms.business.calender;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.*;
import com.wdcloud.lms.business.calender.dto.EventAddDTO;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.business.calender.enums.EveryUnitEnum;
import com.wdcloud.lms.core.base.dao.EventDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JavaDoc")
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_EVENT)
public class EventDataEdit implements IDataEditComponent {
    @Autowired
    private RoleService roleService;
    @Autowired
    private EventDao eventDao;
    /**
     * @api {post} /calendarEvent/add 日历-事件创建
     * @apiName calendarEventAdd
     * @apiGroup Calendar
     *
     * @apiParam {Number} calendarType 日历类型, 1: 个人 2: 课程 3: 学习小组
     * @apiParam {Number} [courseId]课程ID
     * @apiParam {Number} [studyGroupId] 小组Id
     * @apiParam {String} title 标题（size=200)
     * @apiParam {String} [description] 描述
     * @apiParam {Number} [location] 位置
     * @apiParam {Number} [address] 地址
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     *
     * @apiParam {Number=0,1} isDuplicate 是否复制
     * @apiParam {Number} [every] 复制间隔
     * @apiParam {Number=0,1,2} [everyUnit] 复制间隔单位 0:day 1:week 2:month
     * @apiParam {Number} [duplicateNum] 复制副本个数
     * @apiParam {Number=0,1} [isCount] 标题是否追加序号
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @ValidationParam(clazz = EventAddDTO.class,groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        EventAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, EventAddDTO.class);
        dto.setUserId(WebContext.getUserId());
        dto.setCreateUserId(WebContext.getUserId());
        dto.setUpdateUserId(WebContext.getUserId());
        verifyPerm(dto);
        //无副本时，直接保存
        if (dto.getIsDuplicate().equals(Status.NO.getStatus())) {
            eventDao.save(dto);
        }else{//有副本时，批量保存
            List<Event> eventList = new ArrayList<>();
            for (int i = 1; i <= dto.getDuplicateNum()+1; i++) {
                Event event = BeanUtil.beanCopyProperties(dto,Event.class);
                if (dto.getIsCount().equals(Status.YES.getStatus())) {//标题加序号
                    event.setTitle(event.getTitle()+i);
                }
                //如果开始结束时间不空，那么根据复制间隔和单位  确定副本的开始，结束时间
                if (event.getStartTime()!=null&&event.getEndTime()!=null) {
                    buildStartEndTime(dto,event,i);
                }
                eventList.add(event);
            }
            eventDao.batchInsert(eventList);
        }
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * 增加 编辑 删除
     * @param dto
     */
    private void verifyPerm(EventAddDTO dto) {
        if (dto.getCourseId() != null) {//事件类型为 课程或者小组时校验权限
            boolean studentRole =roleService.hasStudentRole();
            if (studentRole) {
                if (CalendarTypeEnum.COURSE.getValue().equals(dto.getCalendarType())) {
                    log.info("学生角色不能操作日历课程事件！");
                    throw new PermissionException();
                }
            }else{
                if (CalendarTypeEnum.STYDYGROUP.getValue().equals(dto.getCalendarType())) {
                    log.info("教师角色不能操作日历小组事件！");
                    throw new PermissionException();
                }
            }
        }

    }

    /**
     * 构建副本的开始结束时间
     * @param dto
     * @param event
     * @param index
     */
    private void buildStartEndTime(EventAddDTO dto, Event event, Integer index) {
        int everyNum=dto.getEvery() * (index - 1);
        if (EveryUnitEnum.DAY.getValue().equals(dto.getEveryUnit())){
            event.setStartTime(DateUtils.addDays(event.getStartTime(),everyNum));
            event.setEndTime(DateUtils.addDays(event.getEndTime(),everyNum));
        }else if(EveryUnitEnum.WEEK.getValue().equals(dto.getEveryUnit())){
            event.setStartTime(DateUtils.addWeeks(event.getStartTime(),everyNum));
            event.setEndTime(DateUtils.addWeeks(event.getEndTime(),everyNum));
        }else if(EveryUnitEnum.MONTH.getValue().equals(dto.getEveryUnit())){
            event.setStartTime(DateUtils.addMonths(event.getStartTime(),everyNum));
            event.setEndTime(DateUtils.addMonths(event.getEndTime(),everyNum));
        }
    }


    /**
     * @api {post} /calendarEvent/modify 日历-事件编辑
     * @apiName calendarEventModify
     * @apiGroup Calendar
     *
     * @apiParam {Number} id 日历事件ID
     * @apiParam {Number} calendarType 日历类型, 1: 个人 2: 课程 3: 学习小组
     * @apiParam {Number} [courseId]课程ID
     * @apiParam {Number} [studyGroupId] 小组Id
     * @apiParam {String} title 标题（size=200)
     * @apiParam {String} [description] 描述
     * @apiParam {Number} [location] 位置
     * @apiParam {Number} [address] 地址
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @ValidationParam(clazz = EventAddDTO.class,groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        EventAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, EventAddDTO.class);
        Event event=eventDao.findOne(Event.builder().id(dto.getId()).build());
        if (event == null) {
            throw new ParamErrorException();
        }
        verifyPerm(dto);
        eventDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /calendarEvent/deletes 日历事件删除
     * @apiName calendarEventDeletes
     * @apiGroup Calendar
     *
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] ID列表
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        int count=eventDao.deletes(idList);
        return new LinkedInfo(count+"");
    }
}

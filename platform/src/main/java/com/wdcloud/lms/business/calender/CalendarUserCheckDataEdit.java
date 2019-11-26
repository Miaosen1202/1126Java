package com.wdcloud.lms.business.calender;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.calender.dto.CalendarItemDTO;
import com.wdcloud.lms.business.calender.dto.UserTodoAddDTO;
import com.wdcloud.lms.core.base.dao.CalendarUserCheckDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.model.CalendarUserCheck;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_ITEM_CHECK)
public class CalendarUserCheckDataEdit implements IDataEditComponent {

    @Autowired
    private CalendarUserCheckDao calendarUserCheckDao;

    /**
     * @api {post} /calendarItemCheck/add 日历项目选中记录添加
     * @apiName calendarItemCheckAdd
     * @apiGroup Calendar
     *
     * @apiParam {Object[]} format 格式:日历类型_ID (日历类型, 1: 个人 2: 课程 3: 学习小组)  如 [1_123]
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity]
     */
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        List<String> selectedContexts = JSON.parseArray(dataEditInfo.beanJson, String.class);
        List<CalendarUserCheck> calendarUserCheckList = new ArrayList<>();
        selectedContexts.forEach(item -> {
            CalendarUserCheck calendarUserCheck = new CalendarUserCheck();
            String[] typeAndId =item.split("_");
            calendarUserCheck.setCalendarType(Integer.valueOf(typeAndId[0]));
            calendarUserCheck.setCheckId(Long.valueOf(typeAndId[1]));
            calendarUserCheck.setUserId(WebContext.getUserId());
            calendarUserCheckList.add(calendarUserCheck);
        });

        calendarUserCheckDao.deleteByField(CalendarUserCheck.builder().userId(WebContext.getUserId()).build());
        if (!calendarUserCheckList.isEmpty()) {
            calendarUserCheckDao.batchInsert(calendarUserCheckList);
        }
        return new LinkedInfo(dataEditInfo.beanJson);
    }
}

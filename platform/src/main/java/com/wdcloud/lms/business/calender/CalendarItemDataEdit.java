package com.wdcloud.lms.business.calender;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.calender.dto.CalendarItemDTO;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_ITEM)
public class CalendarItemDataEdit implements IDataEditComponent {

    @Autowired
    private UserConfigDao userConfigDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    /**
     * @api {post} /calendarItem/modify 日历项目前景色修改
     * @apiName calendarItemModify
     * @apiGroup Calendar
     *
     * @apiParam {Number} calendarType 日历类型, 1: 个人 2: 课程 3: 小组
     * @apiParam {Number} id ID
     * @apiParam {String} coverColor 前景色
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 修改成功条数
     */
    @Override
    @ValidationParam(clazz = CalendarItemDTO.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        CalendarItemDTO dto = JSON.parseObject(dataEditInfo.beanJson, CalendarItemDTO.class);
        int count=0;
        if (CalendarTypeEnum.PERSON.getValue().equals(dto.getCalendarType())) {
            //个人前景色修改
            UserConfig config = userConfigDao.findOne(UserConfig.builder().userId(WebContext.getUserId()).build());
            if (config != null) {
                config.setCoverColor(dto.getCoverColor());
                count=userConfigDao.update(config);
            }
        }else if(CalendarTypeEnum.COURSE.getValue().equals(dto.getCalendarType())) {
             //用户课程前景色修改
            CourseUser courseUser = courseUserDao.findOne(CourseUser.builder().userId(WebContext.getUserId()).courseId(dto.getId().longValue()).build());
            if (courseUser!= null) {
                courseUser.setCoverColor(dto.getCoverColor());
                count=courseUserDao.update(courseUser);
            }
        }else if(CalendarTypeEnum.STYDYGROUP.getValue().equals(dto.getCalendarType())) {
            //用户小组前景色修改
            StudyGroupUser studyGroupUser = studyGroupUserDao.findOne(StudyGroupUser.builder().userId(WebContext.getUserId()).studyGroupId(dto.getId().longValue()).build());
            if (studyGroupUser != null) {
                studyGroupUser.setCoverColor(dto.getCoverColor());
                count = studyGroupUserDao.update(studyGroupUser);
            }
        }
        return new LinkedInfo(String.valueOf(count));
    }

}

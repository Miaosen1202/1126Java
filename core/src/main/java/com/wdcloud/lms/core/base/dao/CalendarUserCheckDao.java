package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.CalendarUserCheckExtMapper;
import com.wdcloud.lms.core.base.model.CalendarUserCheck;
import com.wdcloud.lms.core.base.vo.CalendarItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CalendarUserCheckDao extends CommonDao<CalendarUserCheck, Long> {
    @Autowired
    private CalendarUserCheckExtMapper calendarUserCheckExtMapper;
    @Override
    protected Class<CalendarUserCheck> getBeanClass() {
        return CalendarUserCheck.class;
    }

    public CalendarItemVo findUserCalendarItem(Map<String, Object> param) {
        return calendarUserCheckExtMapper.findUserCalendarItem(param);
    }

    public List<CalendarItemVo> findCourseCalendarItem(Map<String, Object> param) {
        return calendarUserCheckExtMapper.findCourseCalendarItem(param);
    }

    public List<CalendarItemVo> findGroupCalendarItem(Map<String, Object> param) {
        return calendarUserCheckExtMapper.findGroupCalendarItem(param);
    }

    public void batchInsert(List<CalendarUserCheck> calendarUserCheckList) {
        calendarUserCheckExtMapper.batchInsert(calendarUserCheckList);
    }
}
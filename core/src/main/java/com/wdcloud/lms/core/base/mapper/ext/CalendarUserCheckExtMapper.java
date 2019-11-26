package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.CalendarUserCheck;
import com.wdcloud.lms.core.base.vo.CalendarItemVo;

import java.util.List;
import java.util.Map;

public interface CalendarUserCheckExtMapper {
    CalendarItemVo findUserCalendarItem(Map<String, Object> param);

    List<CalendarItemVo> findCourseCalendarItem(Map<String, Object> param);

    List<CalendarItemVo> findGroupCalendarItem(Map<String, Object> param);

    void batchInsert(List<CalendarUserCheck> calendarUserCheckList);
}

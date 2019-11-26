package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Event;
import com.wdcloud.lms.core.base.vo.EventVo;

import java.util.List;
import java.util.Map;

public interface EventExtMapper {
    int batchInsert(List<Event> eventList);

    List<EventVo> findEventList(Map<String, String> param);

    List<EventVo> findEventListByCourse(Map<String, String> param);
}

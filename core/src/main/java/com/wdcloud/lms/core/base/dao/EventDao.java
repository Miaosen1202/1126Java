package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.EventExtMapper;
import com.wdcloud.lms.core.base.model.Event;
import com.wdcloud.lms.core.base.vo.EventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EventDao extends CommonDao<Event, Long> {
    @Autowired
    private EventExtMapper eventExtMapper;
    @Override
    protected Class<Event> getBeanClass() {
        return Event.class;
    }

    public int batchInsert(List<Event> eventList) {
       return eventExtMapper.batchInsert(eventList);
    }

    public List<EventVo> findEventList(Map<String, String> param) {
        return eventExtMapper.findEventList(param);
    }

    public List<EventVo> findEventListByCourse(Map<String, String> param) {
        return eventExtMapper.findEventListByCourse(param);
    }
}
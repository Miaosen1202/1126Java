package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.DiscussionExtMapper;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.vo.CalendarDiscussionVo;
import com.wdcloud.lms.core.base.vo.CalendarQuizVo;
import com.wdcloud.lms.core.base.vo.DialogVo;
import com.wdcloud.lms.core.base.vo.DiscussionListVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DiscussionDao extends CommonDao<Discussion, Long> {
    @Autowired
    private DiscussionExtMapper discussionExtMapper;

    /**
     * 获取课程讨论数量
     * @param courseIds
     * @return (courseId, discussionNumber)
     */
    public Map<Long, Integer> findCourseDiscussionNumber(List<Long> courseIds) {
        if (ListUtils.isEmpty(courseIds)) {
            return new HashMap<>();
        }
        List<Map<String, Long>> courseDiscussionNumber = discussionExtMapper.findCourseDiscussionNumber(courseIds);

        Map<Long, Integer> result = new HashMap<>(courseDiscussionNumber.size());
        for (Map<String, Long> stringLongMap : courseDiscussionNumber) {
            result.put(stringLongMap.get("courseId"), stringLongMap.get("discussionNumber").intValue());
        }
        return result;
    }
    public List<DiscussionListVo> searchCourseDiscussionByTeacher(Map<String, String> param){
        return discussionExtMapper.searchCourseDiscussionByTeacher(param);
    }
    @Override
    protected Class<Discussion> getBeanClass() {
        return Discussion.class;
    }

    public List<DiscussionListVo> searchCourseDiscussionByStudent(Map<String, String> param) {
        return discussionExtMapper.searchCourseDiscussionByStudent(param);
    }

    public List<DiscussionListVo> searchGroupDiscussion(Map<String, String> param) {
        return discussionExtMapper.searchGroupDiscussion(param);
    }

    public List<CalendarDiscussionVo> findCalendarDiscussionList(Map<String, Object> map) {
        return discussionExtMapper.findCalendarDiscussionList(map);
    }

    public List<CalendarDiscussionVo> findCalendarDiscussionListStudent(Map<String, Object> map) {
        return discussionExtMapper.findCalendarDiscussionListStudent(map);
    }

    public List<DialogVo> findDialogs(Long courseId, Long userId) {
        return discussionExtMapper.findDialogs(courseId, userId);
    }
}
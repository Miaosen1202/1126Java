package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.CalendarDiscussionVo;
import com.wdcloud.lms.core.base.vo.CalendarQuizVo;
import com.wdcloud.lms.core.base.vo.DialogVo;
import com.wdcloud.lms.core.base.vo.DiscussionListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DiscussionExtMapper {

    /**
     * 查询课程讨论数量
     * @param courseIds
     * @return
     */
    List<Map<String, Long>>  findCourseDiscussionNumber(List<Long> courseIds);

    List<DiscussionListVo> searchCourseDiscussionByTeacher(Map<String, String> param);

    List<DiscussionListVo> searchCourseDiscussionByStudent(Map<String, String> param);

    List<DiscussionListVo> searchGroupDiscussion(Map<String, String> param);

    List<CalendarDiscussionVo> findCalendarDiscussionList(Map<String, Object> map);

    List<CalendarDiscussionVo> findCalendarDiscussionListStudent(Map<String, Object> map);

    List<DialogVo> findDialogs(@Param("courseId") Long courseId, @Param("userId") Long userId);
}

package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.AnnounceVo;
import com.wdcloud.lms.core.base.vo.DialogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnnounceExtMapper {
    List<Map<String, Long>> findCourseAnnounceNumber(List<Long> courseIds);

    List<AnnounceVo> searchCourseAnnounceByTeacher(Map<String, String> param);

    List<AnnounceVo> searchGroupAnnounceByTeacher(Map<String, String> param);

    List<AnnounceVo> searchCourseAnnounceByStudent(Map<String, String> param);

    List<AnnounceVo> searchGroupAnnounceByStudent(Map<String, String> param);

    List<DialogVo> findMessages(@Param("courseId") Long courseId, @Param("userId") Long userId);
}

package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseJoinedQueryVo {
    private List<CourseJoinedVo> currentEnrollments = new ArrayList<>();
    private List<CourseJoinedVo> priorEnrollments = new ArrayList<>();
    private List<CourseJoinedVo> futureEnrollments = new ArrayList<>();
}

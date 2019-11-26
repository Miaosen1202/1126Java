package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.model.CourseNav;
import lombok.Data;

import java.util.List;

@Data
public class CourseNavVo {
    private Long courseId;
    private List<CourseNav> navList;
}

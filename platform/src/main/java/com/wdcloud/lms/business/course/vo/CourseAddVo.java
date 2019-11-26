package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.model.Course;
import lombok.Data;

@Data
public class CourseAddVo extends Course {
    private String alias;
    private String coverColor;
}

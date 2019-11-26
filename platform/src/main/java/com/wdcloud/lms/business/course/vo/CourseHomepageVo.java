package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.business.course.enums.CourseHomepageTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseHomepageVo {
    @NotNull
    private Long courseId;
    @NotNull
    private CourseHomepageTypeEnum homepage;
}

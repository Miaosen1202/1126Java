package com.wdcloud.lms.business.course.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CourseAliasVo {
    @NotNull
    private Long courseId;
    @Size(max = 30)
    private String alias;

    private String coverColor;
}

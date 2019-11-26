package com.wdcloud.lms.business.people.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseUserDeleteVo {
    @NotNull
    private Long courseId;
    @NotNull
    private Long userId;
}

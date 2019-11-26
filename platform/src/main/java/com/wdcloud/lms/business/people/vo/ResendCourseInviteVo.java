package com.wdcloud.lms.business.people.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResendCourseInviteVo {
    @NotNull
    private Long courseId;
    @NotNull
    private Long userId;
}

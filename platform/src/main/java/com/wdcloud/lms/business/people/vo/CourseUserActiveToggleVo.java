package com.wdcloud.lms.business.people.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class CourseUserActiveToggleVo {
    @NotNull
    private Long courseId;
    @NotNull
    private Long userId;
    @NotNull
    @Range(min = 0, max = 1)
    private Integer activeStatus;
}

package com.wdcloud.lms.business.people.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseUserAddSectionVo {
    @NotNull
    private Long courseId;
    @NotNull
    private Long userId;
    @NotNull
    private Long sectionId;
}

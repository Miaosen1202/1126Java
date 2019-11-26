package com.wdcloud.lms.business.assignment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignmentSubmitCommentDTO {
    @NotNull
    private Long assignmentId;
    @NotNull
    private Long courseId;
    @NotNull
    private String comment;
}

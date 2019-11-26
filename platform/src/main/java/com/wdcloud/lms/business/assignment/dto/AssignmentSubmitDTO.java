package com.wdcloud.lms.business.assignment.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AssignmentSubmitDTO {
    @NotNull
    private Long assignmentId;
    @NotNull
    private String content;
    @NotNull
    private Integer replyType;
    private String comment;
}

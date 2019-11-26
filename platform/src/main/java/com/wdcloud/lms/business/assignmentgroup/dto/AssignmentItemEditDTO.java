package com.wdcloud.lms.business.assignmentgroup.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class AssignmentItemEditDTO {
    @NotNull
    private Long id;
    private Integer score;
    @NotNull
    private String name;
    private int status;
}

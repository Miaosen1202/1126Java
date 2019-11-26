package com.wdcloud.lms.business.assignment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class AssignmentItemMoveDTO {
    private Long sourceAssignmentGroupId;
    private Long sourceAssignmentGroupItemId;
    private Long targetAssignmentGroupId;
    private Long targetAssignmentGroupItemId;
    @NotNull
    private Integer strategy;
}

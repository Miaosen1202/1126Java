package com.wdcloud.lms.business.assignmentgroup.dto;

import com.wdcloud.lms.core.base.model.AssignmentGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class AssignmentGroupWeightEditDTO {
    private List<AssignmentGroup> weights;
    private Integer isWeightGrade;
    private String courseId;
}

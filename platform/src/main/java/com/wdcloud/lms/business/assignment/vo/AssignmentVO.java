package com.wdcloud.lms.business.assignment.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.Assignment;
import lombok.Data;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class AssignmentVO extends Assignment {
    private Long assignmentGroupId;
    private Boolean studyGroupEdited;
    private List<Assign> assigns;
    private AssignUser assignUser;
    private String studyGroupSetName;
    private int submitCount;
}

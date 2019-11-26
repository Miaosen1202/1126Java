package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentGroupItemVo extends AssignmentGroupItem {
    private Long courseId;
    private String groupName;
    List<Assign> assigns;
}

package com.wdcloud.lms.business.assignmentgroup.vo;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Module;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentGroupItemByDateVO{
    private List<AssignmentGroupItemVO> overdue = Lists.newArrayList();
    private List<AssignmentGroupItemVO> upcoming = Lists.newArrayList();
    private List<AssignmentGroupItemVO> undated = Lists.newArrayList();
    private List<AssignmentGroupItemVO> past = Lists.newArrayList();
}

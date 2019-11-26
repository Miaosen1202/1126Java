package com.wdcloud.lms.business.assignmentgroup.vo;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Module;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentGroupItemVO extends AssignmentGroupItem {
    private List<Module> modules = Lists.newArrayList();//被引用的单元
    private List<Assign> assigns = Lists.newArrayList();//被分配的列表
}

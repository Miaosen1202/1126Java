package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.business.strategy.Context;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class AssignmentGroupItemMoveContext extends Context {
    private AssignmentGroupItemMoveStrategy strategy;

    public AssignmentGroupItemMoveContext(AssignmentGroupItemMoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Long sourceItemId, Long targetGroupId, Long targetItemId) {
        strategy.move(sourceItemId, targetGroupId, targetItemId);
    }
}

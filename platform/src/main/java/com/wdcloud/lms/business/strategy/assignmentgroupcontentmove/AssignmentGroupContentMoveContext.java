package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.Context;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class AssignmentGroupContentMoveContext extends Context {
    private AssignmentGroupContentMoveStrategy strategy;

    public AssignmentGroupContentMoveContext(AssignmentGroupContentMoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Long sourceGroupId, Long targetGroupId, Long targetItemId) {
        strategy.move(sourceGroupId, targetGroupId, targetItemId);
    }
}

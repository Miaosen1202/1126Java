package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.Strategy;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface AssignmentGroupContentMoveStrategy extends Strategy {
    /**
     * 本组移动或跨组移动
     *
     * @param sourceGroupId sourceGroupId
     * @param targetGroupId targetGroupId
     * @param targetItemId  targetItemId
     */
    void move(Long sourceGroupId, Long targetGroupId, Long targetItemId);

    MoveStrategyEnum support();
}

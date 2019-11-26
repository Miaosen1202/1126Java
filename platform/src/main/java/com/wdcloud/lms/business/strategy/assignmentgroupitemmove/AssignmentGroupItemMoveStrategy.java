package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.Strategy;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface AssignmentGroupItemMoveStrategy extends Strategy {
    /**
     * 本组移动或跨组移动
     *
     * @param id            itemId
     * @param targetGroupId 目标GroupId
     * @param targetItemId  目标itemId
     */
    void move(Long sourceItemId, Long targetGroupId, Long targetItemId);

    MoveStrategyEnum support();
}

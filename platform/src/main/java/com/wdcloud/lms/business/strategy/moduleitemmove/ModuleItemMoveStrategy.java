package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.Strategy;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface ModuleItemMoveStrategy extends Strategy {
    /**
     * 本组移动或跨组移动
     *
     * @param id             itemId
     * @param targetModuleId 目标moduleId
     * @param targetItemId   目标itemId
     */
    void move(Long sourceItemId, Long targetModuleId, Long targetItemId);

    MoveStrategyEnum support();
}

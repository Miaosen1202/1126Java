package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.business.strategy.Context;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class ModuleItemMoveContext extends Context {
    private ModuleItemMoveStrategy strategy;

    public ModuleItemMoveContext(ModuleItemMoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Long sourceItemId, Long targetModuleId, Long targetItemId) {
        strategy.move(sourceItemId, targetModuleId, targetItemId);
    }
}

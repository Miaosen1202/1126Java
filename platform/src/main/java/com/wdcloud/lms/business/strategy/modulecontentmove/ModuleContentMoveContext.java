package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.business.strategy.Context;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class ModuleContentMoveContext extends Context {
    private ModuleContentMoveStrategy strategy;

    public ModuleContentMoveContext(ModuleContentMoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Long moduleId, Long targetModuleId, Long targetItemId) {
        strategy.move(moduleId, targetModuleId, targetItemId);
    }
}

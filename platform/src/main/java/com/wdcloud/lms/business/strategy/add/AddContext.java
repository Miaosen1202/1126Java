package com.wdcloud.lms.business.strategy.add;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;

/**
 * @author WangYaRong
 */
public class AddContext {
    private AddStrategy strategy;

    public AddContext(AddStrategy strategy) {
        this.strategy = strategy;
    }

    public void add(ModuleItemContentDTO moduleItemContentDTO) {
        this.strategy.add(moduleItemContentDTO);
    }
}

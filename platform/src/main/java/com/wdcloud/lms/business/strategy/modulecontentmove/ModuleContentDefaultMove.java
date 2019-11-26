package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.AbstractDefaultMoveStrategy;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ModuleContentDefaultMove extends AbstractDefaultMoveStrategy implements ModuleContentMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceModuleId, Long targetModuleId, Long targetItemId) {
        List<ModuleItem> moduleItems = moduleItemDao.find(ModuleItem.builder().moduleId(sourceModuleId).build());
        for (ModuleItem moduleItem : moduleItems) {
            moduleItem.setModuleId(targetModuleId);
        }
        moduleItemDao.updateBatchSeqAndModuleId(moduleItems);
    }

    @Override
    public MoveStrategyEnum support() {
        return MoveStrategyEnum.DEFAULT;

    }
}

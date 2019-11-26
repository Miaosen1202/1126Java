package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.business.strategy.AbstractTopMoveStrategy;
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
public class ModuleContentTopMove extends AbstractTopMoveStrategy implements ModuleContentMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceModuleId, Long targetModuleId, Long targetItemId) {
        Integer minSeq = moduleItemDao.ext().getMinSeq(targetModuleId);
        List<ModuleItem> moduleItems = moduleItemDao.find(ModuleItem.builder().moduleId(sourceModuleId).build());
        if (minSeq == null) {
            minSeq = 1;
        }
        for (int i = moduleItems.size() - 1; i >= 0; i--) {
            ModuleItem moduleItem = moduleItems.get(i);
            moduleItem.setModuleId(targetModuleId);
            moduleItem.setSeq(minSeq - moduleItems.size() + i);
        }
        moduleItemDao.updateBatchSeqAndModuleId(moduleItems);
    }
}

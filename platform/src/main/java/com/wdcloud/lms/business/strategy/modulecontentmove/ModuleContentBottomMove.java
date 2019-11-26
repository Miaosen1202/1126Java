package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.business.strategy.AbstractBottomMoveStrategy;
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
public class ModuleContentBottomMove extends AbstractBottomMoveStrategy implements ModuleContentMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceModuleId, Long targetModuleId, Long targetItemId) {
        Integer maxSeq = moduleItemDao.ext().getMaxSeq(targetModuleId);
        List<ModuleItem> moduleItems = moduleItemDao.find(ModuleItem.builder().moduleId(sourceModuleId).build());
        if (maxSeq == null) {
            maxSeq = 0;
        }
        for (int i = 0; i < moduleItems.size(); i++) {
            ModuleItem item = moduleItems.get(i);
            item.setSeq(maxSeq + i + 1);
            item.setModuleId(targetModuleId);
        }
        moduleItemDao.updateBatchSeqAndModuleId(moduleItems);

    }
}

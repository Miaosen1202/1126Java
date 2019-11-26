package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.business.strategy.AbstractBottomMoveStrategy;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ModuleItemBottomMove extends AbstractBottomMoveStrategy implements ModuleItemMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetModuleId, Long targetItemId) {
        ModuleItem moduleItem = moduleItemDao.get(sourceItemId);
        final Integer maxSeq = moduleItemDao.ext().getMaxSeq(targetModuleId);
        if (maxSeq == null) {
            moduleItemDao.updateSeq(sourceItemId, targetModuleId, 1);
            return;
        }
        if (!(moduleItem.getModuleId().equals(targetModuleId) && moduleItem.getSeq().equals(maxSeq))) {
            //如果是一个组里的 并且是组里最大的那个 就不用再置底
            moduleItemDao.updateSeq(sourceItemId, targetModuleId, maxSeq + 1);
        }

    }
}

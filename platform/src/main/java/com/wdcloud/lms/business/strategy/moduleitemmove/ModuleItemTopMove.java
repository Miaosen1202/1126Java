package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.business.strategy.AbstractTopMoveStrategy;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ModuleItemTopMove extends AbstractTopMoveStrategy implements ModuleItemMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @Override
    public void move(Long sourceItemId, Long targetModuleId, Long targetItemId) {
        ModuleItem moduleItem = moduleItemDao.get(sourceItemId);
        final Integer minSeq = moduleItemDao.ext().getMinSeq(targetModuleId);
        if (minSeq == null) {//目标组里没有内容
            moduleItemDao.updateSeq(sourceItemId, targetModuleId, 1);
            return;
        }
        if (!(moduleItem.getModuleId().equals(targetModuleId) && moduleItem.getSeq().equals(minSeq))) {
            //如果是一个组里的 并且是组里最小的那个 就不用再置顶
            moduleItemDao.updateSeq(sourceItemId, targetModuleId, minSeq - 1);
        }

    }
}

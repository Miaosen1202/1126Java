package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.AbstractDefaultMoveStrategy;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ModuleItemDefaultMove extends AbstractDefaultMoveStrategy implements ModuleItemMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetModuleId, Long targetItemId) {
        moduleItemDao.updateSeq(sourceItemId, targetModuleId, 1);
    }

    @Override
    public MoveStrategyEnum support() {
        return MoveStrategyEnum.DEFAULT;
    }
}

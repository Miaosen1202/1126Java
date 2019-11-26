package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.business.strategy.AbstractAfterMoveStrategy;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ModuleContentAfterMove extends AbstractAfterMoveStrategy implements ModuleContentMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceModuleId, Long targetModuleId, Long targetItemId) {
        //需要移动的content
        final List<ModuleItem> targetItems = moduleItemDao.getModuleItemOrderBySeq(targetModuleId);
        List<ModuleItem> sourceItems = moduleItemDao.find(ModuleItem.builder().moduleId(sourceModuleId).build());
        //找到指定的那个
        int tmpSeq = -1;
        int tmpIndex = -1;
        for (int i = 0; i < targetItems.size(); i++) {
            if (targetItems.get(i).getId().equals(targetItemId)) {
                tmpSeq = targetItems.get(i).getSeq();
                tmpIndex = i;
                break;
            }
        }
        List<ModuleItem> data = new ArrayList<>(targetItems.size() + sourceItems.size());
        for (int i = 0; i < sourceItems.size(); i++) {
            ModuleItem item = sourceItems.get(i);
            item.setModuleId(targetModuleId);
            item.setSeq(tmpSeq + i + 1);
            data.add(item);
        }
        //更新target之后的seq
        for (int i = tmpIndex + 1; i < targetItems.size(); i++) {
            final ModuleItem item = targetItems.get(i);
            if (!item.getId().equals(targetItemId)) {
                item.setModuleId(targetModuleId);
                item.setSeq(tmpSeq + sourceItems.size() + 1);
                data.add(item);
            }
        }
        moduleItemDao.updateBatchSeqAndModuleId(data);
    }
}

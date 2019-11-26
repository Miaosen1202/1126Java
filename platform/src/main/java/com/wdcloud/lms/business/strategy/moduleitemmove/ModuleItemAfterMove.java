package com.wdcloud.lms.business.strategy.moduleitemmove;

import com.wdcloud.lms.business.strategy.AbstractAfterMoveStrategy;
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
public class ModuleItemAfterMove extends AbstractAfterMoveStrategy implements ModuleItemMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetModuleId, Long targetItemId) {
        //获取全部
        final List<ModuleItem> items = moduleItemDao.getModuleItemOrderBySeq(targetModuleId);
        //找到指定的那个
        int tmpIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            ModuleItem item = items.get(i);
            if (item.getId().equals(targetItemId)) {
                tmpIndex = i;
                item.setModuleId(targetModuleId);
                item.setSeq(item.getSeq() + 1);
                break;
            }
        }
        //更新target之后的seq
        for (int i = tmpIndex + 1; i < items.size(); i++) {
            final ModuleItem item = items.get(i);
            if (!item.getId().equals(sourceItemId)) {
                item.setModuleId(targetModuleId);
                item.setSeq(item.getSeq() + 1);
            }
        }
        //批量更新
        moduleItemDao.updateBatchSeqAndModuleId(items);
    }
}

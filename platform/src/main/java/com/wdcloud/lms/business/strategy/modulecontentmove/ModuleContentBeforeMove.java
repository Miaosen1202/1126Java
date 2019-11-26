package com.wdcloud.lms.business.strategy.modulecontentmove;

import com.wdcloud.lms.business.strategy.AbstractBeforeMoveStrategy;
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
public class ModuleContentBeforeMove extends AbstractBeforeMoveStrategy implements ModuleContentMoveStrategy {
    @Autowired
    private ModuleItemDao moduleItemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long moduleId, Long targetModuleId, Long targetItemId) {
        //需要移动的content
        List<ModuleItem> sourceItems = moduleItemDao.find(ModuleItem.builder().moduleId(moduleId).build());
        //目标content
        final List<ModuleItem> targetItems = moduleItemDao.getModuleItemOrderBySeq(targetModuleId);
        //找到指定的那个
        int tmpIndex = -1;
        int tmpSeq = -1;
        for (int i = 0; i < targetItems.size(); i++) {
            if (targetItems.get(i).getId().equals(targetItemId)) {
                //设置当前为target -1
                tmpIndex = i;
                tmpSeq = targetItems.get(i).getSeq();
                break;
            }
        }
        List<ModuleItem> data = new ArrayList<>(targetItems.size() + sourceItems.size());

        //插入需要移动的content
        for (int i = sourceItems.size() - 1; i >= 0; i--) {
            ModuleItem item = sourceItems.get(i);
            item.setModuleId(targetModuleId);
            item.setSeq(tmpSeq - sourceItems.size() + i);
            data.add(item);
        }
        //更新target之前的seq
        for (int i = tmpIndex - 1; i >= 0; i--) {
            final ModuleItem item = targetItems.get(i);
            if (!item.getId().equals(targetItemId)) {
                item.setModuleId(targetModuleId);
                item.setSeq(tmpSeq - sourceItems.size() - 1);
                data.add(item);
            }
        }
        moduleItemDao.updateBatchSeqAndModuleId(data);
    }
}

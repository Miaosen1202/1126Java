package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.AbstractDefaultMoveStrategy;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentGroupContentDefaultMove extends AbstractDefaultMoveStrategy implements AssignmentGroupContentMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceModuleId, Long targetGroupId, Long targetItemId) {
        List<AssignmentGroupItem> items = itemDao.find(AssignmentGroupItem.builder().assignmentGroupId(sourceModuleId).build());
        for (AssignmentGroupItem item : items) {
            item.setAssignmentGroupId(targetGroupId);
        }
        itemDao.updateBatchSeqAndGroupId(items);
    }
}

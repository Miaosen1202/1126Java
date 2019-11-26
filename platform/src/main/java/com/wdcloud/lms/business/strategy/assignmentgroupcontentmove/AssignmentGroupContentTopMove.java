package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.AbstractTopMoveStrategy;
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
public class AssignmentGroupContentTopMove extends AbstractTopMoveStrategy implements AssignmentGroupContentMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceGroupId, Long targetGroupId, Long targetItemId) {
        Integer minSeq = itemDao.ext().getMinSeq(targetGroupId);
        List<AssignmentGroupItem> items = itemDao.find(AssignmentGroupItem.builder().assignmentGroupId(sourceGroupId).build());
        if (minSeq == null) {
            minSeq = 1;
        }
        for (int i = items.size() - 1; i >= 0; i--) {
            AssignmentGroupItem item = items.get(i);
            item.setAssignmentGroupId(targetGroupId);
            item.setSeq(minSeq - items.size() + i);
        }
        if (items.size() > 0) {
            itemDao.updateBatchSeqAndGroupId(items);
        }
    }
}

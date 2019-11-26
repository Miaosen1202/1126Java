package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.AbstractBottomMoveStrategy;
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
public class AssignmentGroupContentBottomMove extends AbstractBottomMoveStrategy implements AssignmentGroupContentMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceGroupId, Long targetGroupId, Long targetItemId) {
        Integer maxSeq = itemDao.ext().getMaxSeq(targetGroupId);
        List<AssignmentGroupItem> items = itemDao.find(AssignmentGroupItem.builder().assignmentGroupId(sourceGroupId).build());
        if (maxSeq == null) {
            maxSeq = 0;
        }
        for (int i = 0; i < items.size(); i++) {
            AssignmentGroupItem item = items.get(i);
            item.setSeq(maxSeq + i + 1);
            item.setAssignmentGroupId(targetGroupId);
        }
        itemDao.updateBatchSeqAndGroupId(items);
    }

}

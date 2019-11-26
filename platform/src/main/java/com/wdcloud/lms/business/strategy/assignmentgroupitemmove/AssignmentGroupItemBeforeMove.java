package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.business.strategy.AbstractBeforeMoveStrategy;
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
public class AssignmentGroupItemBeforeMove extends AbstractBeforeMoveStrategy implements AssignmentGroupItemMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetGroupId, Long targetItemId) {
        final List<AssignmentGroupItem> items = itemDao.getAssignmentGroupItemOrderBySeq(targetGroupId);
        //找到指定的那个
        int tmpIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            AssignmentGroupItem item = items.get(i);
            if (item.getId().equals(targetItemId)) {
                //设置当前为target -1
                tmpIndex = i;
                item.setSeq(item.getSeq() - 1);
                item.setAssignmentGroupId(targetGroupId);
                break;
            }
        }
        //更新target之前的seq
        for (int i = tmpIndex - 1; i >= 0; i--) {
            final AssignmentGroupItem item = items.get(i);
            if (!item.getId().equals(targetItemId)) {
                item.setSeq(item.getSeq() - 1);
                item.setAssignmentGroupId(targetGroupId);
            }
        }
        itemDao.updateBatchSeqAndGroupId(items);
    }
}

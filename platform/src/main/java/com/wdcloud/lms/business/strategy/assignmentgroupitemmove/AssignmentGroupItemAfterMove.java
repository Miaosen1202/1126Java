package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.business.strategy.AbstractAfterMoveStrategy;
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
public class AssignmentGroupItemAfterMove extends AbstractAfterMoveStrategy implements AssignmentGroupItemMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetGroupId, Long targetItemId) {
        //获取全部
        final List<AssignmentGroupItem> items = itemDao.getAssignmentGroupItemOrderBySeq(targetGroupId);
        //找到指定的那个
        int tmpIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            AssignmentGroupItem item = items.get(i);
            if (item.getId().equals(targetItemId)) {
                tmpIndex = i;
                item.setAssignmentGroupId(targetGroupId);
                item.setSeq(item.getSeq() + 1);
                break;
            }
        }
        //更新target之后的seq
        for (int i = tmpIndex + 1; i < items.size(); i++) {
            final AssignmentGroupItem item = items.get(i);
            if (!item.getId().equals(sourceItemId)) {
                item.setAssignmentGroupId(targetGroupId);
                item.setSeq(item.getSeq() + 1);
            }
        }
        itemDao.updateBatchSeqAndGroupId(items);
    }
}

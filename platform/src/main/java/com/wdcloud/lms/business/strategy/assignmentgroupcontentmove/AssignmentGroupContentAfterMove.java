package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.AbstractAfterMoveStrategy;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentGroupContentAfterMove extends AbstractAfterMoveStrategy implements AssignmentGroupContentMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceGroupId, Long targetGroupId, Long targetItemId) {
        //需要移动的content
        final List<AssignmentGroupItem> targetItems = itemDao.getAssignmentGroupItemOrderBySeq(targetGroupId);
        List<AssignmentGroupItem> sourceItems = itemDao.find(AssignmentGroupItem.builder().assignmentGroupId(sourceGroupId).build());
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
        List<AssignmentGroupItem> data = new ArrayList<>(targetItems.size() + sourceItems.size());
        for (int i = 0; i < sourceItems.size(); i++) {
            AssignmentGroupItem item = sourceItems.get(i);
            item.setAssignmentGroupId(targetGroupId);
            item.setSeq(tmpSeq + i + 1);
            data.add(item);
        }
        //更新target之后的seq
        for (int i = tmpIndex + 1; i < targetItems.size(); i++) {
            final AssignmentGroupItem item = targetItems.get(i);
            if (!item.getId().equals(targetItemId)) {
                item.setAssignmentGroupId(targetGroupId);
                item.setSeq(tmpSeq + sourceItems.size() + 1);
                data.add(item);
            }
        }
        itemDao.updateBatchSeqAndGroupId(data);
    }
}

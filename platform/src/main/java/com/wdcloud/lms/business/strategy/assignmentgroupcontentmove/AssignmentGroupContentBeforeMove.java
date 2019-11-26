package com.wdcloud.lms.business.strategy.assignmentgroupcontentmove;

import com.wdcloud.lms.business.strategy.AbstractBeforeMoveStrategy;
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
public class AssignmentGroupContentBeforeMove extends AbstractBeforeMoveStrategy implements AssignmentGroupContentMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceGroupId, Long targetGroupId, Long targetItemId) {
        //需要移动的content
        List<AssignmentGroupItem> sourceItems = itemDao.find(AssignmentGroupItem.builder().assignmentGroupId(sourceGroupId).build());
        //目标content
        final List<AssignmentGroupItem> targetItems = itemDao.getAssignmentGroupItemOrderBySeq(targetGroupId);
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
        List<AssignmentGroupItem> data = new ArrayList<>(targetItems.size() + sourceItems.size());

        //插入需要移动的content
        for (int i = sourceItems.size() - 1; i >= 0; i--) {
            AssignmentGroupItem item = sourceItems.get(i);
            item.setAssignmentGroupId(targetGroupId);
            item.setSeq(tmpSeq - sourceItems.size() + i);
            data.add(item);
        }
        //更新target之前的seq
        for (int i = tmpIndex - 1; i >= 0; i--) {
            final AssignmentGroupItem item = targetItems.get(i);
            if (!item.getId().equals(targetItemId)) {
                item.setAssignmentGroupId(targetGroupId);
                item.setSeq(tmpSeq - sourceItems.size() - 1);
                data.add(item);
            }
        }
        itemDao.updateBatchSeqAndGroupId(data);
    }
}

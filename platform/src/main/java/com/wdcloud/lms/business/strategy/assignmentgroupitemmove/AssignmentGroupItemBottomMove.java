package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.business.strategy.AbstractBottomMoveStrategy;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentGroupItemBottomMove extends AbstractBottomMoveStrategy implements AssignmentGroupItemMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetGroupId, Long targetItemId) {
        final Integer maxSeq = itemDao.ext().getMaxSeq(targetGroupId);
        AssignmentGroupItem item = itemDao.get(sourceItemId);
        if (maxSeq == null) {
            itemDao.updateSeq(sourceItemId, targetGroupId, 1);
            return;
        }
        //如果是一个组里的 并且是组里最小的那个 就不用再置底
        if (!(item.getAssignmentGroupId().equals(targetGroupId) && item.getSeq().equals(maxSeq))) {
            itemDao.updateSeq(sourceItemId, targetGroupId, maxSeq + 1);
        }
    }
}

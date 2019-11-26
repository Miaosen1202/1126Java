package com.wdcloud.lms.business.strategy.assignmentgroupitemmove;

import com.wdcloud.lms.business.strategy.AbstractDefaultMoveStrategy;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentGroupItemDefaultMove extends AbstractDefaultMoveStrategy implements AssignmentGroupItemMoveStrategy {
    @Autowired
    private AssignmentGroupItemDao itemDao;

    @SuppressWarnings("Duplicates")
    @Override
    public void move(Long sourceItemId, Long targetGroupId, Long targetItemId) {
        itemDao.updateSeq(sourceItemId, targetGroupId, 1);
    }
}

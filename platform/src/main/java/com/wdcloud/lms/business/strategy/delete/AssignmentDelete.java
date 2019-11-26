package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractAssignmentStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.AssignmentReply;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentDelete extends AbstractAssignmentStrategy implements DeleteStrategy {

    @Override
    public Integer delete(Long id) {
        // 作业提交记录
        assignmentReplyDao.deleteByField(AssignmentReply.builder().assignmentId(id).build());
        // 作业组关联关系
        assignmentGroupItemDao.deleteByField(AssignmentGroupItem.builder().originId(id)
                .originType(OriginTypeEnum.ASSIGNMENT.getType()).build());
        // 单元作业关联关系
        moduleItemDao.deleteByField(ModuleItem.builder()
                .originType(OriginTypeEnum.ASSIGNMENT.getType()).originId(id).build());
        // 分配记录表
        assignDao.deleteByField(Assign.builder().originId(id)
                .originType(OriginTypeEnum.ASSIGNMENT.getType()).build());
        return assignmentDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return assignmentDao.deletes(ids);
    }

}

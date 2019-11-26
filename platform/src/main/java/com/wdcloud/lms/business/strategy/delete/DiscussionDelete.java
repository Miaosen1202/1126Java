package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.strategy.AbstractDiscussionStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Discussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class DiscussionDelete extends AbstractDiscussionStrategy implements DeleteStrategy {
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    @Override
    public Integer delete(Long id) {
        moduleCompleteService.deleteModuleItemByOriginId(id, OriginTypeEnum.DISCUSSION.getType());
        // 分配记录表
        assignDao.deleteByField(Assign.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
        //分配学生关系表
        assignUserDao.deleteByField(AssignUser.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
        // 作业组关联关系表
        assignmentGroupItemDao.deleteByField(AssignmentGroupItem.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
        return discussionDao.update(Discussion.builder().id(id).isDeleted(Status.YES.getStatus()).build());
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return discussionDao.deletes(ids);
    }
}

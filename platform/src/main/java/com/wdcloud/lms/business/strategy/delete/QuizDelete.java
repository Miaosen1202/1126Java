package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractQuizStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class QuizDelete extends AbstractQuizStrategy implements DeleteStrategy {
    @Override
    public Integer delete(Long id) {
        //分配关系表
        assignDao.deleteByField(Assign.builder().originId(id).originType(OriginTypeEnum.QUIZ.getType()).build());
        //分配学生关系表
        assignUserDao.deleteByField(AssignUser.builder().originId(id).originType(OriginTypeEnum.QUIZ.getType()).build());
        // 作业组关联关系表
        assignmentGroupItemDao.deleteByField(AssignmentGroupItem.builder().originId(id).originType(OriginTypeEnum.QUIZ.getType()).build());

        return quizDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return quizDao.deletes(ids);
    }
}

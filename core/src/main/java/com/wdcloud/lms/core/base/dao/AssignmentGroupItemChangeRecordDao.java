package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.AssignmentGroupItemChangeOpTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentGroupItemChangeRecordExtMapper;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentGroupItemChangeRecord;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssignmentGroupItemChangeRecordDao extends CommonDao<AssignmentGroupItemChangeRecord, Long> {
    @Autowired
    private AssignmentGroupItemChangeRecordExtMapper changeRecordExtMapper;

    public AssignmentGroupItemChangeRecord discussionAdded(Discussion discussion, Long assignmentItemId) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(discussion.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(discussion.getTitle())
                .content(discussion.getContent())
                .originId(discussion.getId())
                .originType(OriginTypeEnum.DISCUSSION.getType())
                .opType(AssignmentGroupItemChangeOpTypeEnum.CREATE.getType())
                .build();
        save(record);
        return record;
    }

    public AssignmentGroupItemChangeRecord quizAdded(Quiz quiz, Long assignmentItemId) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(quiz.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(quiz.getTitle())
                .content(quiz.getDescription())
                .originId(quiz.getId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .opType(AssignmentGroupItemChangeOpTypeEnum.CREATE.getType())
                .build();
        save(record);
        return record;
    }

    public AssignmentGroupItemChangeRecord assignmentAdded(Assignment assignment, Long assignmentItemId) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(assignment.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(assignment.getTitle())
                .content(assignment.getDescription())
                .originId(assignment.getId())
                .originType(OriginTypeEnum.ASSIGNMENT.getType())
                .opType(AssignmentGroupItemChangeOpTypeEnum.CREATE.getType())
                .build();
        save(record);
        return record;
    }

    public AssignmentGroupItemChangeRecord changed(Discussion discussion, Long assignmentItemId,
                                                            AssignmentGroupItemChangeOpTypeEnum opTypeEnum) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(discussion.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(discussion.getTitle())
                .content(discussion.getContent())
                .originId(discussion.getId())
                .originType(OriginTypeEnum.DISCUSSION.getType())
                .opType(opTypeEnum.getType())
                .build();
        save(record);
        return record;
    }

    public AssignmentGroupItemChangeRecord changed(Quiz quiz, Long assignmentItemId,
                                                            AssignmentGroupItemChangeOpTypeEnum opTypeEnum) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(quiz.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(quiz.getTitle())
                .content(quiz.getDescription())
                .originId(quiz.getId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .opType(opTypeEnum.getType())
                .build();
        save(record);
        return record;
    }

    public AssignmentGroupItemChangeRecord changed(Assignment assignment, Long assignmentItemId,
                                                            AssignmentGroupItemChangeOpTypeEnum opTypeEnum) {
        AssignmentGroupItemChangeRecord record = AssignmentGroupItemChangeRecord.builder()
                .courseId(assignment.getCourseId())
                .assignmentGroupItemId(assignmentItemId)
                .title(assignment.getTitle())
                .content(assignment.getDescription())
                .originId(assignment.getId())
                .originType(OriginTypeEnum.ASSIGNMENT.getType())
                .opType(opTypeEnum.getType())
                .build();
        save(record);
        return record;
    }

    public List<AssignmentGroupItemChangeRecord> findNotifies(Long courseId) {
        return changeRecordExtMapper.findNotifies(courseId);
    }

    @Override
    protected Class<AssignmentGroupItemChangeRecord> getBeanClass() {
        return AssignmentGroupItemChangeRecord.class;
    }
}

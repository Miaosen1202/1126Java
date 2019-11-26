package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class QuizQuestionReplyRecordDTO extends QuizQuestionReplyRecord {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 问题记录ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizQuestionRecordId;

    /**
     * 回答内容
     */
    private String reply;
}
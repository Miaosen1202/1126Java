package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionRecordVO extends QuizQuestionRecord {

    /**
     * 选择题选项
     */
    private List<QuizQuestionOptionRecord> options;
    /**
     * 匹配题
     */
    private List<QuizQuestionMatchOptionRecord> matchoptions;
    /**
     * 简答题
     */
    private QuizQuestionReplyRecord reply;
}

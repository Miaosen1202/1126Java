package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.lms.core.base.model.QuizItem;
import lombok.Data;

import java.util.List;
@Data
public class QuestionVO extends Question {
    /**
     * 选择题选项
     */
    private List<QuestionOption> options;

    /**
     * 匹配题
     */
    private List<QuestionMatchOption> matchoptions;
    /**
     * 测验问题项
     */
    QuizItem quizItem;
}

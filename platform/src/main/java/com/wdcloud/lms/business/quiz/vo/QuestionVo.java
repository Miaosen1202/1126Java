package com.wdcloud.lms.business.quiz.vo;

import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuestionOption;
import lombok.Data;

import java.util.List;

@Data
public class QuestionVo extends Question {
    private List<QuestionOption> questionOptions;
}

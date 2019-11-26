package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.QuestionGroup;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.lms.core.base.model.QuizItem;
import lombok.Data;

import java.util.List;

@Data
public class QuizItemVO extends QuizItem {

    /**
     * 问题基本信息
     */
    private QuestionVO question;
    /**
     * 问题组信息
     */
    private QuestionGroup questionGroup;

    /**
     * 选择题选项
     */
   private List<QuestionOption> options;

    /**
     * 匹配题
     */
   private List<QuestionMatchOption> matchoptions;


}

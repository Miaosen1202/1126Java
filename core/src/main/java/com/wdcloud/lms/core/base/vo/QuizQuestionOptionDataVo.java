package com.wdcloud.lms.core.base.vo;

import lombok.Data;

/**
 * @author zhangxutao
 */
@Data
public class QuizQuestionOptionDataVo {
    private Long id;
    private Long optionId;
    private Long quizQuestionRecordId;
    private Long questionOptionId;
    private String code;
    private String contents;
    private String explanation;
    private Integer isCorrect;
    private Integer optionSeq;
    private Integer isChoice;
}

package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

/**
 * @author zhangxutao
 */
@Data
public class QuizQuestionOptionVo {
    private Long optionId;
    private Long quizQuestionRecordId;
    private Long questionOptionId;
    private String code;
    private String content;
    private String explanation;
    private Integer isCorrect;
    private Integer seq;
    private Integer isChoice;

}

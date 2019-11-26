package com.wdcloud.lms.core.base.vo;

/**
 * @author zhangxutao
 */

import lombok.Data;

@Data
public class QuizSubjectivityDataListVo {
    private Long id;
    private Long questionsId;
    private String questionsTitle;
    private String content;
    private Integer seq;
    private String answer;
    private Integer score;
    private Integer gradeScore;
    private Long quizRecordId;
    private String correctComment;
    private String wrongComment;
    private String generalComment;
}

package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhangxutao
 * 测验-客观题
 */
@Data
public class QuizObjectiveListVo {
    private Long id;
    private Long questionId;
    private String questionsTitle;
    private String content;
    private Integer type;
    private List<QuizQuestionOptionVo> option;
    private Integer seq;
    private Integer score;
    private Integer gradeScore;
    private Long quizRecordId;
    private String correctComment;
    private String wrongComment;
    private String generalComment;


}

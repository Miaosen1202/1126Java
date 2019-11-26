package com.wdcloud.lms.core.base.vo;


import java.util.List;
import lombok.Data;

/**
 * @author zhangxutao
 */


@Data
public class QuizObjectiveDataListVo {

    private Long id;
    private Long questionId;
    private Long quizRecordId;
    private String questionsTitle;
    private String content;
    private Integer type;
    private Integer seq;
    private Integer score;
    private Integer gradeScore;
    private String correctComment;
    private String wrongComment;
    private String generalComment;
    private List<QuizQuestionOptionDataVo> option;
}

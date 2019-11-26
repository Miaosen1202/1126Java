package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

/**
 * @author zhangxutao
 * 测验-主观题
 */
@Data
public class QuizSubjectivityListVo {
    private Integer id;
    private String questionsId;
    private String questionsTitle;
    private String content;
    private String seq;
    private String answer;
    private Integer score;
    private Integer gradeScore;
    private Integer quizRecordId;
    private String correctComment;
    private String wrongComment;
    private String generalComment;


}

package com.wdcloud.lms.business.quiz.dto;

import lombok.Data;


@Data
public class QuizScoreRecordDTO  {
    /**
     * 测验ID
     */
      private Long quizId;

    /**
     * 测验人ID
     */
    private Long testerId;
    /**
     * 时间限制
     */
    private Integer timeLimit;
    /**
     * 已答题次数
     */
    private Integer attemps;
    /**
     * 剩余答题次数
     */
    private Integer attempsAvailable;
    /**
     * 是否全部评分
     */
    private Integer isGradedAll;
    /**
     * 当前分数
     */
    private Integer currentScore;
    /**
     * 最终为准的分数
     */
    private Integer keptScore;

    /**
     * 评分规则
     */
    private Integer gradeStrategy;

    /**
     * 测验记录ID,改id为kept记录的id
     */
    private Long quizRecordId;


}
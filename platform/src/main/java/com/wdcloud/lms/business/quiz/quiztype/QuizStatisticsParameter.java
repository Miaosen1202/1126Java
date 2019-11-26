package com.wdcloud.lms.business.quiz.quiztype;

import lombok.Data;

@Data
public class QuizStatisticsParameter {
    /**
     * 上次该题或该组的分数
     */
    private int lastScore;
    /**
     * 当前该题或该组的分数
     */
    private int currentScore;
    /**
     * 上次问题个数
     */
    private int lastQuestions;
    /**
     * 当前问题个数
     */
    private int currentQuestions;
}

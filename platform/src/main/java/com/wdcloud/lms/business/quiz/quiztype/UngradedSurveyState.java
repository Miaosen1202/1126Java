package com.wdcloud.lms.business.quiz.quiztype;

import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public class UngradedSurveyState extends QuizTypeState {
    //分数统计
    public int scoreStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        return 0;
    }

}

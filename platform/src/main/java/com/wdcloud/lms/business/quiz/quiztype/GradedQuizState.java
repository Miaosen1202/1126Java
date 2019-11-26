package com.wdcloud.lms.business.quiz.quiztype;

import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public class GradedQuizState extends QuizTypeState {
    //分数统计
    public int scoreStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        int totalScores = 0;
        switch (operation) {
            case QuestionOperationConstants.ADD:
                //添加
                totalScores = quiz.getTotalScore() + quizStatisticsParameter.getCurrentScore();
                break;
            case QuestionOperationConstants.MODIFY:
                //修改
                totalScores = quiz.getTotalScore() + quizStatisticsParameter.getCurrentScore() - quizStatisticsParameter.getLastScore();
                break;
            case QuestionOperationConstants.DELETE:
                //删除
                totalScores = quiz.getTotalScore() - quizStatisticsParameter.getLastScore();
                break;
            case QuestionOperationConstants.MOVE_TO_GROUP:
                //问题移动到组
                totalScores = quiz.getTotalScore() - quizStatisticsParameter.getLastScore();
                break;
            case QuestionOperationConstants.REMOVE_FROM_GROUP:
                //问题从组里移出
                totalScores = quiz.getTotalScore() + quizStatisticsParameter.getCurrentScore();
                break;
            default:
                //;
                totalScores = quiz.getTotalScore();
                break;
        }
        return totalScores;
    }

}

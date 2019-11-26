package com.wdcloud.lms.business.quiz.quiztype;

import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.stereotype.Service;

/**
 * 功能：该类对应状态模式的抽象状态类
 *
 * @author 黄建林
 */
@Service
public abstract class QuizTypeState {
    /**
     * 功能：分数统计
     */
    public abstract int scoreStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter);

    /**
     * 功能：问题数统计算法
     * @param quiz
     * @param operation
     * @param quizStatisticsParameter
     * @return
     */
    public int questionStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        int totalQuestions = 0;
        switch (operation) {
            case QuestionOperationConstants.ADD:
                //添加
                totalQuestions = quiz.getTotalQuestions() + quizStatisticsParameter.getCurrentQuestions();
                break;
            case QuestionOperationConstants.MODIFY:
                //修改
                totalQuestions = quiz.getTotalQuestions() - quizStatisticsParameter.getLastQuestions() + quizStatisticsParameter.getCurrentQuestions();
                break;
            case QuestionOperationConstants.DELETE:
                //删除
                totalQuestions = quiz.getTotalQuestions() - quizStatisticsParameter.getLastQuestions();
                break;
            case QuestionOperationConstants.MOVE_TO_GROUP:
                //问题移动到组
                totalQuestions = quiz.getTotalQuestions() - quizStatisticsParameter.getLastQuestions();

                break;
            case QuestionOperationConstants.REMOVE_FROM_GROUP:
                //问题从组里移出
                totalQuestions = quiz.getTotalQuestions() + quizStatisticsParameter.getLastQuestions();

                break;
            default:
                //;
                totalQuestions = quiz.getTotalQuestions();
                break;
        }
        return totalQuestions;
    }
}

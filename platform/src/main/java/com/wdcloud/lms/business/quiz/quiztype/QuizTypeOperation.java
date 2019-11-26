package com.wdcloud.lms.business.quiz.quiztype;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：该类对应状态模式的环境类
 *
 * @author 黄建林
 */

@Service
public class QuizTypeOperation {
    @Qualifier("QuizTypeState")
    private QuizTypeState quizTypeState;
    @Resource(name = "gradedQuizState")
    private GradedQuizState gradedQuizState;
    @Resource(name = "gradedSurveyState")
    private GradedSurveyState gradedSurveyState;
     @Resource(name = "practiceQuizState")
    private PracticeQuizState practiceQuizState;
    @Resource(name = "ungradedSurveyState")
    private UngradedSurveyState ungradedSurveyState;

    /**
     * 功能：分数统计
     * @param quiz
     * @param operation
     * @param quizStatisticsParameter
     * @return
     */
    public int scoreStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        changeType(quiz.getType());
        if (quizTypeState == null) {
            return QuizTypeConstants.OPERATE_NONE;
        }
        return quizTypeState.scoreStatistics(quiz, operation, quizStatisticsParameter);
    }

    /**
     * 功能：问题数统计
     * @param quiz
     * @param operation
     * @param quizStatisticsParameter
     * @return
     */
    public int questionStatistics(Quiz quiz, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        changeType(quiz.getType());
        if (quizTypeState == null) {
            return QuizTypeConstants.OPERATE_NONE;
        }
        return quizTypeState.questionStatistics(quiz, operation, quizStatisticsParameter);
    }



    /**
     * 功能：依据传入不同类型的测验创建不同测验的对象
     * 参数： type 测验类型
     */
    private void changeType(Integer type) {
        if (type == null) {
            quizTypeState = null;
            throw new BaseException("param.must.not.be.null",type+"");
        }
        switch (type) {
            case QuizTypeConstants.PRACTICE_QUIZ:
                //练习测验;
                quizTypeState = practiceQuizState;
                break;
            case QuizTypeConstants.GRADED_QUIZ:
                //评分测验;
                quizTypeState = gradedQuizState;
                break;
            case QuizTypeConstants.GRADED_SURVEY:
                //评分调查;
                quizTypeState = gradedSurveyState;
                break;
            case QuizTypeConstants.UNGRADED_SURVEY:
                //非评分调查;
                quizTypeState = ungradedSurveyState;
                break;
            default:
                //;
                quizTypeState = null;
                break;
        }


    }

}

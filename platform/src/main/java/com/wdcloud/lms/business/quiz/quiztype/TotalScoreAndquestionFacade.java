package com.wdcloud.lms.business.quiz.quiztype;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：该类对应外观模式的外观类
 *
 * @author 黄建林
 */
@Service
public class TotalScoreAndquestionFacade {

    @Autowired
    private QuizTypeOperation quizTypeOperation;
    @Autowired
    private QuizDao quizDao;

    public int questionsAndScoresUpdate(Long quizId, int operation, QuizStatisticsParameter quizStatisticsParameter) {
        Quiz dto = quizDao.get(quizId);
        if (null == dto) {
            return QuizTypeConstants.OPERATE_NONE;
        }
        int totalScores = quizTypeOperation.scoreStatistics(dto, operation, quizStatisticsParameter);
        if (QuizTypeConstants.OPERATE_NONE == totalScores) {
            totalScores = dto.getTotalScore();
        }
        int totalQuestions = quizTypeOperation.questionStatistics(dto, operation, quizStatisticsParameter);
        if (QuizTypeConstants.OPERATE_NONE == totalQuestions) {
            totalQuestions = dto.getTotalQuestions();
        }
        dto.setTotalScore(totalScores);
        dto.setTotalQuestions(totalQuestions);
        dto.setUpdateUserId(WebContext.getUserId());
        return quizDao.questionsAndScoresUpdate(dto);
    }

}

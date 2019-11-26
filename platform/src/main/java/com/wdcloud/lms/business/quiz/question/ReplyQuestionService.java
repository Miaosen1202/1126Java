package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.core.base.dao.QuizQuestionReplyRecordDao;
import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：回答类问题
 *
 * @author 黄建林
 */
@Slf4j
@Service
public class ReplyQuestionService {
    @Autowired
    private QuizQuestionReplyRecordDao quizQuestionReplyRecordDao;

    public void saveRecord(QuizQuestionReplyRecord quizQuestionReplyRecord, long questionId) {

        quizQuestionReplyRecord.setQuizQuestionRecordId(questionId);
        quizQuestionReplyRecordDao.save(quizQuestionReplyRecord);
        log.info("[ReplyQuestionService] batch save reply success, quizQuestionRecordId={}, ",
                questionId);
    }

    public void updateRecord(QuizQuestionReplyRecord quizQuestionReplyRecord) {
        quizQuestionReplyRecordDao.update(quizQuestionReplyRecord);
        log.info("[ReplyQuestionService] batch update reply success");
    }

    public void deleteRecord(QuizQuestionReplyRecord quizQuestionReplyRecord) {
        quizQuestionReplyRecordDao.delete(quizQuestionReplyRecord.getId());
        log.info("[OptionService] batch delete reply success");
    }
}

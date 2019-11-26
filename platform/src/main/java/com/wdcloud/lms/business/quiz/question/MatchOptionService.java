package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.QuestionMatchOptionDao;
import com.wdcloud.lms.core.base.dao.QuizQuestionMatchOptionRecordDao;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：匹配题批量增加、更新、删除
 *
 * @author 黄建林
 */
@Slf4j
@Service
public class MatchOptionService {
    @Autowired
    private QuestionMatchOptionDao questionMatchOptionDao;
    @Autowired
    private QuizQuestionMatchOptionRecordDao quizQuestionMatchOptionRecordDao;


    public void batchSave(List<QuestionMatchOption> matchOptions, long questionId) {
        for (QuestionMatchOption matchOption : matchOptions) {
            matchOption.setQuestionId(questionId);
            matchOption.setCreateUserId(WebContext.getUserId());
            matchOption.setUpdateUserId(WebContext.getUserId());
        }
        questionMatchOptionDao.batchInsert(matchOptions);
        log.info("[MatchOptionService] batch save matchOption success, courseId={},  assignNumber={}",
                questionId, matchOptions.size());
    }

    public void batchUpdate(List<QuestionMatchOption> matchOptions) {
        for (QuestionMatchOption matchOption : matchOptions) {
            matchOption.setUpdateUserId(WebContext.getUserId());
        }
        questionMatchOptionDao.batchUpdate(matchOptions);
        log.info("[MatchOptionService] batch update matchOption success,assignNumber={}",
                matchOptions.size());
    }

    public void batchDelete(List<QuestionMatchOption> matchOptions) {
        questionMatchOptionDao.batchDelete(matchOptions);
        log.info("[MatchOptionService] batch delete matchOption success,assignNumber={}",
                matchOptions.size());
    }

    /**
     * 功能：依据问题ID查询选项信息
     */
    public QuestionVO getByquestionId(long questionId) {
        return questionMatchOptionDao.getByquestionId(questionId);
    }

    /**
     * 答题记录
     */

    public void batchSaveRecord(List<QuizQuestionMatchOptionRecord> matchOptions, long questionId) {
        for (QuizQuestionMatchOptionRecord matchOption : matchOptions) {
            matchOption.setQuizQuestionRecordId(questionId);
            matchOption.setCreateUserId(WebContext.getUserId());
            matchOption.setUpdateUserId(WebContext.getUserId());
        }
        quizQuestionMatchOptionRecordDao.batchInsert(matchOptions);
        log.info("[MatchOptionService] batch save matchOption record success, courseId={},  assignNumber={}",
                questionId, matchOptions.size());
    }

    public void batchUpdateRecord(List<QuizQuestionMatchOptionRecord> matchOptions) {
        for (QuizQuestionMatchOptionRecord matchOption : matchOptions) {
            matchOption.setUpdateUserId(WebContext.getUserId());
        }
        quizQuestionMatchOptionRecordDao.batchUpdate(matchOptions);
        log.info("[MatchOptionService] batch update matchOption record success,assignNumber={}",
                matchOptions.size());
    }

    public void batchDeleteRecord(List<QuizQuestionMatchOptionRecord> matchOptions) {
        quizQuestionMatchOptionRecordDao.batchDelete(matchOptions);
        log.info("[MatchOptionService] batch delete matchOption record success,assignNumber={}",
                matchOptions.size());
    }


}

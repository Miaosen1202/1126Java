package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.core.base.dao.QuestionOptionDao;
import com.wdcloud.lms.core.base.dao.QuizQuestionOptionRecordDao;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：选项题批量增加、更新、删除
 *
 * @author 黄建林
 */
@Slf4j
@Service
public class OptionService {
    @Autowired
    private QuestionOptionDao questionOptionDao;
    @Autowired
    private QuizQuestionOptionRecordDao quizQuestionOptionRecordDao;


    public void batchSave(List<QuestionOption> options, long questionId) {
        for (QuestionOption option : options) {
            option.setQuestionId(questionId);
            option.setCreateUserId(WebContext.getUserId());
            option.setUpdateUserId(WebContext.getUserId());
        }
        questionOptionDao.batchInsert(options);
        log.info("[OptionService] batch save option success, courseId={},  assignNumber={}",
                questionId, options.size());
    }

    public void batchUpdate(List<QuestionOption> options) {
        for (QuestionOption option : options) {
            option.setUpdateUserId(WebContext.getUserId());
        }
        questionOptionDao.batchUpdate(options);
        log.info("[OptionService] batch update option success,assignNumber={}",
                options.size());
    }

    public void batchDelete(List<QuestionOption> options) {
        questionOptionDao.batchDelete(options);
        log.info("[OptionService] batch delete option success,assignNumber={}",
                options.size());
    }

    /**
     * 功能：依据问题ID查询选项信息
     */
    public QuestionVO getByquestionId(long questionId) {
        return questionOptionDao.getByquestionId(questionId);
    }

    /**
     * 功能：学生答题,选项题批量增加
     */
    public void batchSaveRecord( QuizQuestionRecordDTO dto) {
        List<QuizQuestionOptionRecord> options=dto.getOptions();
        for (QuizQuestionOptionRecord option :options ) {
            option.setQuizQuestionRecordId(dto.getId());
            option.setCreateUserId(WebContext.getUserId());
            option.setUpdateUserId(WebContext.getUserId());

        }
        quizQuestionOptionRecordDao.batchInsert(options);
        log.info("[OptionService] batch save option record success, courseId={},  assignNumber={}",
                dto.getId(), options.size());
    }

    /**
     * 功能：学生答题,选项题批量更新
     */
    public void batchUpdateRecord(QuizQuestionRecordDTO dto) {
        List<QuizQuestionOptionRecord> options=dto.getOptions();
        for (QuizQuestionOptionRecord option : options) {
            option.setUpdateUserId(WebContext.getUserId());
        }
        quizQuestionOptionRecordDao.batchUpdate(options);
        log.info("[OptionService] batch update option record success,assignNumber={}",
                options.size());
    }

    /**
     * 功能：学生答题,选项题批量更新
     */
    public void batchDeleteRecord(QuizQuestionRecordVO dto) {
        List<QuizQuestionOptionRecord> options=dto.getOptions();
        for (QuizQuestionOptionRecord option : options) {
            option.setUpdateUserId(WebContext.getUserId());
        }
        quizQuestionOptionRecordDao.batchDelete(options);
        log.info("[OptionService] batch update option record success,assignNumber={}",
                options.size());
    }

}

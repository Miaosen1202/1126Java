package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuizQuestionRecordExtMapper;
import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizQuestionRecordDao extends CommonDao<QuizQuestionRecord, Long> {
    @Autowired
    private QuizQuestionRecordExtMapper quizQuestionRecordExtMapper;

    @Override
    protected Class<QuizQuestionRecord> getBeanClass() {
        return QuizQuestionRecord.class;
    }

    public List<QuizQuestionRecordVO> getQuerstionRecords(QuizQuestionRecord quizQuestionRecord) {
        return quizQuestionRecordExtMapper.getQuerstionRecords(quizQuestionRecord);
    }

    public int getTotalRecordScores(long quizRecordId) {
        return quizQuestionRecordExtMapper.getTotalRecordScores(quizRecordId);
    }


}
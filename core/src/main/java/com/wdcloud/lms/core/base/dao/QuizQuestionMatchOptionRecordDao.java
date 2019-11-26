package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuizQuestionMatchOptionRecordExtMapper;
import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizQuestionMatchOptionRecordDao extends CommonDao<QuizQuestionMatchOptionRecord, Long> {

    @Autowired
    private QuizQuestionMatchOptionRecordExtMapper quizQuestionMatchOptionRecordDaoExtMapper;

    @Override
    protected Class<QuizQuestionMatchOptionRecord> getBeanClass() {
        return QuizQuestionMatchOptionRecord.class;
    }

    public int batchInsert(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList) {
        return quizQuestionMatchOptionRecordDaoExtMapper.batchInsert(quizQuestionMatchOptionRecordList);
    }

    public int batchUpdate(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList) {
        return quizQuestionMatchOptionRecordDaoExtMapper.batchUpdate(quizQuestionMatchOptionRecordList);
    }

    public int batchDelete(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList) {
        return quizQuestionMatchOptionRecordDaoExtMapper.batchDelete(quizQuestionMatchOptionRecordList);
    }


}
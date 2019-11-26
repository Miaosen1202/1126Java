package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuizQuestionOptionRecordExtMapper;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizQuestionOptionRecordDao extends CommonDao<QuizQuestionOptionRecord, Long> {

    @Autowired
    private QuizQuestionOptionRecordExtMapper quizQuestionOptionRecordExtMapper;

    @Override
    protected Class<QuizQuestionOptionRecord> getBeanClass() {
        return QuizQuestionOptionRecord.class;
    }

    public void batchInsert(List<QuizQuestionOptionRecord> quizQuestionOptionRecordnList) {
        if (ListUtils.isNotEmpty(quizQuestionOptionRecordnList)) {
            quizQuestionOptionRecordExtMapper.batchInsert(quizQuestionOptionRecordnList);
        }
    }

    public void batchUpdate(List<QuizQuestionOptionRecord> quizQuestionOptionRecordnList) {
        if (ListUtils.isNotEmpty(quizQuestionOptionRecordnList)) {
            quizQuestionOptionRecordExtMapper.batchUpdate(quizQuestionOptionRecordnList);
        }
    }

    public void batchDelete(List<QuizQuestionOptionRecord> quizQuestionOptionRecordnList) {
        if (ListUtils.isNotEmpty(quizQuestionOptionRecordnList)) {
            quizQuestionOptionRecordExtMapper.batchDelete(quizQuestionOptionRecordnList);
        }
    }


}
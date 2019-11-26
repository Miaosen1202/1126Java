package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import org.springframework.stereotype.Repository;

@Repository
public class QuizQuestionReplyRecordDao extends CommonDao<QuizQuestionReplyRecord, Long> {

    @Override
    protected Class<QuizQuestionReplyRecord> getBeanClass() {
        return QuizQuestionReplyRecord.class;
    }
}
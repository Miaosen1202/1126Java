package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.QuestionBank;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionBankDao extends CommonDao<QuestionBank, Long> {

    @Override
    public Class<QuestionBank> getBeanClass() {
        return QuestionBank.class;
    }


}
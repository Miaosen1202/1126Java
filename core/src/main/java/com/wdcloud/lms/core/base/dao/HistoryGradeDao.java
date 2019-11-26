package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.HistoryGrade;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryGradeDao extends CommonDao<HistoryGrade, Long> {

    @Override
    protected Class<HistoryGrade> getBeanClass() {
        return HistoryGrade.class;
    }
}
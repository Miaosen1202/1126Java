package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.CertificationUserRejectHistory;
import org.springframework.stereotype.Repository;

@Repository
public class CertificationUserRejectHistoryDao extends CommonDao<CertificationUserRejectHistory, Long> {

    @Override
    protected Class<CertificationUserRejectHistory> getBeanClass() {
        return CertificationUserRejectHistory.class;
    }
}
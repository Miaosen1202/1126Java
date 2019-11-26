package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.CertificationHistoryUser;
import org.springframework.stereotype.Repository;

@Repository
public class CertificationHistoryUserDao extends CommonDao<CertificationHistoryUser, Long> {

    @Override
    protected Class<CertificationHistoryUser> getBeanClass() {
        return CertificationHistoryUser.class;
    }
}
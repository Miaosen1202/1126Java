package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.OrgEmail;
import org.springframework.stereotype.Repository;

@Repository
public class OrgEmailDao extends CommonDao<OrgEmail, Long> {

    @Override
    protected Class<OrgEmail> getBeanClass() {
        return OrgEmail.class;
    }
}
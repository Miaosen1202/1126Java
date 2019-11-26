package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.OrgUser;
import org.springframework.stereotype.Repository;

@Repository
public class OrgUserDao extends CommonDao<OrgUser, Long> {

    @Override
    protected Class<OrgUser> getBeanClass() {
        return OrgUser.class;
    }
}
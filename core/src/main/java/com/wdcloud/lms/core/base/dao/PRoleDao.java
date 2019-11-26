package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.PRole;
import org.springframework.stereotype.Repository;

@Repository
public class PRoleDao extends CommonDao<PRole, Long> {

    @Override
    protected Class<PRole> getBeanClass() {
        return PRole.class;
    }
}
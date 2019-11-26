package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.TermConfig;
import org.springframework.stereotype.Repository;

@Repository
public class TermConfigDao extends CommonDao<TermConfig, Long> {

    @Override
    protected Class<TermConfig> getBeanClass() {
        return TermConfig.class;
    }
}
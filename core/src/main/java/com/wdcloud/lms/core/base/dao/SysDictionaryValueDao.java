package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.SysDictionaryValue;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictionaryValueDao extends CommonDao<SysDictionaryValue, Long> {

    @Override
    protected Class<SysDictionaryValue> getBeanClass() {
        return SysDictionaryValue.class;
    }
}
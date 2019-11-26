package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.Page;
import org.springframework.stereotype.Repository;

@Repository
public class PageDao extends CommonDao<Page, Long> {

    @Override
    protected Class<Page> getBeanClass() {
        return Page.class;
    }
}
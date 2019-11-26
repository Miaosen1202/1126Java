package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.DiscussionSubscribe;
import org.springframework.stereotype.Repository;

@Repository
public class DiscussionSubscribeDao extends CommonDao<DiscussionSubscribe, Long> {

    @Override
    protected Class<DiscussionSubscribe> getBeanClass() {
        return DiscussionSubscribe.class;
    }
}
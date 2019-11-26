package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.GenseeUserLiveHistory;
import com.wdcloud.lms.core.base.model.GenseeUserVodHistory;
import org.springframework.stereotype.Repository;

@Repository
public class GenseeUserVodHistoryDao extends CommonDao<GenseeUserVodHistory, Long> {

    @Override
    protected Class<GenseeUserVodHistory> getBeanClass() {
        return GenseeUserVodHistory.class;
    }
}
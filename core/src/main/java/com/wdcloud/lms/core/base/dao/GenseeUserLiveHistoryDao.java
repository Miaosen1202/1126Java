package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.GenseeUserLiveHistoryExtMapper;
import com.wdcloud.lms.core.base.model.GenseeUserLiveHistory;
import com.wdcloud.lms.core.base.vo.LiveHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GenseeUserLiveHistoryDao extends CommonDao<GenseeUserLiveHistory, Long> {

    @Autowired
    private GenseeUserLiveHistoryExtMapper genseeUserLiveHistoryExtMapper;

    public List<LiveHistoryVO> searchLiveHistory(Map<String, Object> map) {
        return genseeUserLiveHistoryExtMapper.searchLiveHistory(map);
    }

    public List<LiveHistoryVO> searchVodHistory(Map<String, Object> map) {
        return genseeUserLiveHistoryExtMapper.searchVodHistory(map);
    }

    @Override
    protected Class<GenseeUserLiveHistory> getBeanClass() {
        return GenseeUserLiveHistory.class;
    }
}
package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.GenseeLiveExtMapper;
import com.wdcloud.lms.core.base.model.GenseeLive;
import com.wdcloud.lms.core.base.vo.LiveListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GenseeLiveDao extends CommonDao<GenseeLive, Long> {

    @Autowired
    private GenseeLiveExtMapper genseeLiveExtMapper;

    public List<LiveListVO> findLiveByCon(Map<String, Object> map) {
        return genseeLiveExtMapper.findLiveByCon(map);
    }

    public List<LiveListVO> findNotStartedLiveByCon(Map<String, Object> map) {
        return genseeLiveExtMapper.findNotStartedLiveByCon(map);
    }

    public List<LiveListVO> findInProgressLiveByCon(Map<String, Object> map) {
        return genseeLiveExtMapper.findInProgressLiveByCon(map);
    }

    public List<LiveListVO> findFinishedLiveByCon(Map<String, Object> map) {
        return genseeLiveExtMapper.findFinishedLiveByCon(map);
    }

    public List<LiveListVO> findLiveDetail(Map<String, Object> map) {
        return genseeLiveExtMapper.findLiveDetail(map);
    }

    public List<GenseeLive> findGenseeLive(Map<String, String> map) {
        return genseeLiveExtMapper.findGenseeLive(map);
    }

    @Override
    protected Class<GenseeLive> getBeanClass() {
        return GenseeLive.class;
    }
}
package com.wdcloud.lms.base.service;

import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.business.live.dto.LiveDTO;
import com.wdcloud.lms.core.base.vo.LiveHistoryVO;
import com.wdcloud.lms.core.base.vo.LiveListVO;

import java.util.List;
import java.util.Map;

public interface LiveService {
    public Long createLive(LiveDTO dto);
    public void updateLive(LiveDTO dto);
    public void deleteLive(Long id);
    public void endLive(Long id);
    public void syncVod();
    public void syncLiveHistory();
    public void syncVodHistory();
    public List<LiveHistoryVO> searchLiveHistory(Map<String, String> param);
    public List<LiveHistoryVO> searchVodHistory(Map<String, String> param);
    public List<LiveListVO> findLiveByCon(Map<String, Object> map);
    public List<LiveListVO> findNotStartedLiveByCon(Map<String, Object> map);
    public List<LiveListVO> findInProgressLiveByCon(Map<String, Object> map);
    public List<LiveListVO> findFinishedLiveByCon(Map<String, Object> map);
    public List<LiveListVO> findLiveDetail(Map<String, Object> map);
    LiveTypeEnum support();
}

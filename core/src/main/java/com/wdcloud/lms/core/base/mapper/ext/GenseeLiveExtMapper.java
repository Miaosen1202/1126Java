package com.wdcloud.lms.core.base.mapper.ext;

import java.util.List;
import java.util.Map;
import com.wdcloud.lms.core.base.vo.LiveListVO;
import com.wdcloud.lms.core.base.model.GenseeLive;

public interface GenseeLiveExtMapper {
    List<LiveListVO> findLiveByCon(Map<String, Object> map);

    List<LiveListVO> findNotStartedLiveByCon(Map<String, Object> map);

    List<LiveListVO> findInProgressLiveByCon(Map<String, Object> map);

    List<LiveListVO> findFinishedLiveByCon(Map<String, Object> map);

    List<LiveListVO> findLiveDetail(Map<String, Object> map);

    List<GenseeLive> findGenseeLive(Map<String, String> map);
}
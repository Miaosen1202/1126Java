package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.LiveHistoryVO;

import java.util.List;
import java.util.Map;

public interface GenseeUserLiveHistoryExtMapper {
    List<LiveHistoryVO> searchLiveHistory(Map<String, Object> map);
    List<LiveHistoryVO> searchVodHistory(Map<String, Object> map);
}
package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.AnnounceReplyVO;

import java.util.List;
import java.util.Map;

public interface AnnounceReplyExtMapper {
    List<AnnounceReplyVO> findReply(Map<String, String> param);
}

package com.wdcloud.lms.core.base.mapper.ext;


import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;
import com.wdcloud.lms.core.base.vo.ReadCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DiscussionReplyExtMapper {
    List<DiscussionReplyVO> findReply(Map<String, String> param);


    /**
     * 获取作业回复信息
     * @param param
     * @return
     */
    List<DiscussionReplyVO> getDiscussionReplyList(Map<String, String> param);

    ReadCountDTO findDiscussionReplyReadCount(@Param("userId") Long userId, @Param("discussionId") Long discussionId);

}

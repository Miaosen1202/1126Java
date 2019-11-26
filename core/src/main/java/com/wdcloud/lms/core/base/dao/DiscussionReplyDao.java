package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.DiscussionReplyExtMapper;
import com.wdcloud.lms.core.base.model.DiscussionReply;
import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DiscussionReplyDao extends CommonDao<DiscussionReply, Long> {
    @Autowired
    private DiscussionReplyExtMapper discussionReplyExtMapper;
    @Override
    protected Class<DiscussionReply> getBeanClass() {
        return DiscussionReply.class;
    }

    public List<DiscussionReplyVO> findReply(Map<String, String> param){
        return discussionReplyExtMapper.findReply(param);
    }

    /**
     * 获取作业回复信息
     * @param param
     * @return
     */
    public List<DiscussionReplyVO> getDiscussionReplyList(Map<String, String> param){
        return  discussionReplyExtMapper.getDiscussionReplyList(param);
    }

}
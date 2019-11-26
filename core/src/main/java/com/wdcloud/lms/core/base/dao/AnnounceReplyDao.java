package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.AnnounceReplyMapper;
import com.wdcloud.lms.core.base.mapper.ext.AnnounceReplyExtMapper;
import com.wdcloud.lms.core.base.model.AnnounceReply;
import com.wdcloud.lms.core.base.vo.AnnounceReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AnnounceReplyDao extends CommonDao<AnnounceReply, Long> {
    @Autowired
    private AnnounceReplyExtMapper announceReplyExtMapper;

    @Override
    protected Class<AnnounceReply> getBeanClass() {
        return AnnounceReply.class;
    }

    public List<AnnounceReplyVO> findReply(Map<String, String> param) {
        return announceReplyExtMapper.findReply(param);
    }
}
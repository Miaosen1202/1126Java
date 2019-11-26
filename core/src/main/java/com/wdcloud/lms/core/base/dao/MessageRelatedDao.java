package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.MessageRelatedExtMapper;
import com.wdcloud.lms.core.base.model.MessageRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRelatedDao extends CommonDao<MessageRelated, Long> {
    @Autowired
    private MessageRelatedExtMapper messageRelatedExtMapper;
    @Override
    protected Class<MessageRelated> getBeanClass() {
        return MessageRelated.class;
    }

    public void batchSave(List<MessageRelated> messageRelatedList) {
        messageRelatedExtMapper.batchSave(messageRelatedList);
    }
}
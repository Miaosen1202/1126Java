package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.MessageSubjectUserStarExtMapper;
import com.wdcloud.lms.core.base.model.MessageSubjectUserStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageSubjectUserStarDao extends CommonDao<MessageSubjectUserStar, Long> {

    @Autowired
    private MessageSubjectUserStarExtMapper messageSubjectUserStarExtMapper;

    @Override
    protected Class<MessageSubjectUserStar> getBeanClass() {
        return MessageSubjectUserStar.class;
    }

    public int batchSave(List<MessageSubjectUserStar> messageSubjectUserStarList) {
        return messageSubjectUserStarExtMapper.batchSave(messageSubjectUserStarList);
    }
}
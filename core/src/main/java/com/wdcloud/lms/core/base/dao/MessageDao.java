package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.MessageExtMapper;
import com.wdcloud.lms.core.base.model.Message;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.lms.core.base.vo.msg.SubjectInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class MessageDao extends CommonDao<Message, Long> {

    @Autowired
    private MessageExtMapper messageExtMapper;

    @Override
    protected Class<Message> getBeanClass() {
        return Message.class;
    }

    public SubjectInfoVO findSubjectInfoById(Integer subjectId,Long loginUserId) {
        return messageExtMapper.findSubjectInfoById(subjectId,loginUserId);
    }

    public List<MsgItemVO> findMsgItemByMsgIds(Set<Long> messageIds) {
       return messageExtMapper.findMsgItemByMsgIds(messageIds);
    }
}
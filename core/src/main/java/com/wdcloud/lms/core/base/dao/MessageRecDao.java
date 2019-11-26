package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.MessageRecExtMapper;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.vo.msg.MessageSubjectVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessageRecDao extends CommonDao<MessageRec, Long> {

    @Autowired
    private MessageRecExtMapper messageRecExtMapper;
    @Override
    protected Class<MessageRec> getBeanClass() {
        return MessageRec.class;
    }

    public void batchSave(List<MessageRec> messageRecList) {
        messageRecExtMapper.batchSave(messageRecList);
    }

    public List<MessageSubjectVO> findRecSubjectList(Map<String, Object> param) {
        return messageRecExtMapper.findRecSubjectList(param);
    }


    public MsgItemVO myLastestRecMsg(Long subjectId, Long recId) {
        return messageRecExtMapper.myLastestRecMsg(subjectId,recId);
    }

    public List<MsgItemVO> findRecMsgListBySubject(Map<String, String> condition) {
        return messageRecExtMapper.findRecMsgListBySubject(condition);
    }

    public int getUnreadNum(List<Long> courseIds, Long userId) {
        return messageRecExtMapper.getUnreadNum(courseIds,userId);
    }
}
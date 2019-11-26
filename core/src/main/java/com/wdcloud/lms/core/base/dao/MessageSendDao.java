package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.MessageExtMapper;
import com.wdcloud.lms.core.base.model.MessageSend;
import com.wdcloud.lms.core.base.vo.msg.MessageSentVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessageSendDao extends CommonDao<MessageSend, Long> {

    @Autowired
    private MessageExtMapper messageExtMapper;
    @Override
    protected Class<MessageSend> getBeanClass() {
        return MessageSend.class;
    }

    public List<MessageSentVO> findMsgSentList(Map<String, Object> param) {
        //我发送消息所属的主题列表
        List<MessageSentVO> sentSubjectList=messageExtMapper.findMsgSentList(param);
        return sentSubjectList;
    }

    public MsgItemVO myLastestSentMsg(MessageSentVO subject) {
        //获取每个主题下，我发送的最新一条消息
        MsgItemVO lastestSentMsgVO= messageExtMapper.myLastestSentMsg(subject);
        return lastestSentMsgVO;
    }

    /**
     * 获取每个主题下，我发送的所有消息
     * @param condition
     * @return
     */
    public List<MsgItemVO> findSentMsgListBySubject(Map<String, String> condition) {
        List<MsgItemVO> lastestSentMsgVOList=messageExtMapper.findSentMsgListBySubject(condition);
        return lastestSentMsgVOList;
    }
}
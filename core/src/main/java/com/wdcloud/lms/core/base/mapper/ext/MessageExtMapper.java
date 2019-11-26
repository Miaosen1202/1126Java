package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.msg.MessageSentVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.lms.core.base.vo.msg.SubjectInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MessageExtMapper {
    /**
     * 我发送的主题列表
     * @param param
     * @return
     */
    List<MessageSentVO> findMsgSentList(Map<String, Object> param);

    /**
     * 获取每个主题下，我发送的最新一条消息
     * @param subject
     * @return
     */
    MsgItemVO myLastestSentMsg(MessageSentVO subject);

    /**
     * 通过主题ID获取主题消息
     * @param subjectId
     * @return
     */
    SubjectInfoVO findSubjectInfoById(@Param("subjectId") Integer subjectId,@Param("loginUserId") Long loginUserId);

    /**
     * 获取某一主题下我发送的消息列表
     * @param condition
     * @return
     */
    List<MsgItemVO> findSentMsgListBySubject(Map<String, String> condition);

    List<MsgItemVO> findMsgItemByMsgIds(@Param("messageIds") Set<Long> messageIds);
}

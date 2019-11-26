package com.wdcloud.lms.business.message;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.MessageDao;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_HISTORY
)
public class MessageHistoryQuery implements ISelfDefinedSearch<Object> {
    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private MessageReplyRecsQuery messageReplyRecsQuery;
    @Autowired
    private MessageRecDataQuery messageRecDataQuery;

    /**
     *
     * @api {get} /message/history/query 回复|回复所有  消息历史列表
     * @apiName messageHistoryQuery
     * @apiGroup message
     * @apiParam {Number} subjectId 消息主题ID
     * @apiParam {Number} messageId 消息ID
     * @apiParam {Number=0,1} replyType 回复类型 0:回复 1:回复所有
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 收件人集合
     * @apiSuccess {Number} entity.id 收件人ID
     * @apiSuccess {String} entity.username 收件人用户名
     */
    @Override
    public List<MsgItemVO> search(Map<String, String> condition)  {
        Long subjectId = Long.valueOf(condition.get("subjectId"));
        Long messageId = Long.valueOf(condition.get("messageId"));
        Integer replyType = Integer.valueOf(condition.get("replyType"));
        /**
         * 每个收件人 收件历史消息集合 求交集
         */
        //1获取收件人集合
        List<User> recList=messageReplyRecsQuery.search(condition);
        Set<Long> recIds = recList.stream().map(User::getId).collect(Collectors.toSet());
        recIds.add(WebContext.getUserId());
        //2获取这些收件人在当前主题下收到的历史消息
        Example example = messageRecDao.getExample();
        example.createCriteria()
                .andEqualTo(MessageRec.SUBJECT_ID,subjectId)
                .andIn(MessageRec.REC_ID,recIds)
                .andLessThanOrEqualTo(MessageRec.MESSAGE_ID,messageId)
                .andEqualTo(MessageRec.IS_DELETE,0);
        List<MessageRec> messageRecList= messageRecDao.find(example);
        if (ListUtils.isEmpty(messageRecList)) {
            return null;
        }
        Map<Long,Set<Long>> recMsgsMap=messageRecList.stream()
                .collect(Collectors.groupingBy(MessageRec::getRecId,Collectors.mapping(MessageRec::getMessageId, Collectors.toSet())));
        Set<Long> rltMsgIds =recMsgsMap.values().stream().reduce((a,b)->{
            a.retainAll(b);
            return a;
        }).get();
        if (CollectionUtil.isNotNullAndEmpty(rltMsgIds)) {
            List<MsgItemVO> msgItemVOs=messageDao.findMsgItemByMsgIds(rltMsgIds);
            for (MsgItemVO msgItemVO : msgItemVOs) {
                messageRecDataQuery.buildIsPrivate(msgItemVO);
            }
            return msgItemVOs;
        }
        return null;
    }

}

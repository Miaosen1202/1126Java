package com.wdcloud.lms.business.message;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.MessageDao;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Message;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.model.MessageSend;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE_REPLY,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_TO
)
public class MessageReplyRecsQuery implements ISelfDefinedSearch<Object> {
    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    /**
     * @api {get} /messageReply/rec/query 回复|回复所有时  消息收件人列表查询
     * @apiName messageReplyRecQuery
     * @apiGroup message
     * @apiParam {Number} messageId 消息ID
     * @apiParam {Number=0,1} replyType 回复类型 0:回复 1：回复所有
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 收件人集合
     * @apiSuccess {Number} entity.id 收件人ID
     * @apiSuccess {String} entity.username 收件人用户名
     */
    @Override
    public List<User> search(Map<String, String> condition)  {
        Long messageId = Long.valueOf(condition.get("messageId"));
        Integer replyType = Integer.valueOf(condition.get("replyType"));
        /**
         *  获取收件人Ids
         * 发件人为自己，则收件人为 此消息的收件人集合
         * 发件人非自己：
         *   回复: 则收件人为 此消息的 发件人
         *   回复所有：则收件人为 此消息的 发件人+收件人集合
         */
        Set<Long> recIds = new HashSet<>();
        Message message = messageDao.get(messageId);
        //MessageSend messageSend=messageSendDao.findOne(MessageSend.builder().messageId(messageId).build());
        //发件人是自己
        if (WebContext.getUserId().equals(message.getCreateUserId())) {
            recIds.addAll(getRecIds(messageId));
        }else{//发件人非自己
            //回复
            if (replyType.equals(Status.NO.getStatus())) {
                recIds.add(message.getCreateUserId());
            }else{//回复所有
                recIds.add(message.getCreateUserId());
                if(!message.getIsPrivate().equals(Status.YES.getStatus())){
                    recIds.addAll(getRecIds(messageId));
                }
            }
        }
        if (CollectionUtil.isNotNullAndEmpty((recIds))&&recIds.size()>1){
            recIds.remove(WebContext.getUserId());
        }
        //通过收件人Ids批量获取用户名
        List<User> userList = userDao.gets(recIds);
        List<User> users = new ArrayList<>();
        userList.forEach(user->{
            users.add(User.builder().id(user.getId()).username(user.getUsername()).fullName(user.getFullName()).build());
        });
        return users;
    }

    private  Set<Long> getRecIds(Long messageId) {
        List<MessageRec> messageRecList= messageRecDao.find(MessageRec.builder().messageId(messageId).isDefault(0).build());
       return messageRecList.stream().map(MessageRec::getRecId).collect(Collectors.toSet());
    }

}

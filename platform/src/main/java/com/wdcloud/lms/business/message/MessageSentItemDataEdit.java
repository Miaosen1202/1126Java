package com.wdcloud.lms.business.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.message.dto.MessageDelDTO;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Message;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.model.MessageSend;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_ITEM
)
public class MessageSentItemDataEdit implements ISelfDefinedEdit {

    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private MessageRecDao messageRecDao;
    /**
     * @api {post} /message/item/edit 我的 已发送|已接收 消息单个删除
     * @apiName messageItemEdit
     * @apiGroup message
     * @apiParam {Number} messageId 消息ID
     * @apiParam {Number=0,1} type 0:已发送 1:已接收
     * @apiParamExample {json} 请求示例:
     * {id:1,type:0}
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除成功总数
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "1"
     * }
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        MessageDelDTO delDTO = JSON.parseObject(dataEditInfo.beanJson, MessageDelDTO.class);
        int count = 0;
        if (delDTO.getType().equals(0)) {
            //已发送关系删除
            //逻辑删除我的已发送消息关系表
            // count = messageSendDao.deleteByField(MessageSend.builder().messageId(delDTO.getMessageId()).build());
            MessageSend messageSend = messageSendDao.findOne(MessageSend.builder().messageId(delDTO.getMessageId()).isDelete(0).build());
            messageSendDao.updateByExample(MessageSend.builder().isDelete(1).build(), List.of(messageSend.getId()));
        }else{
            //已接收关系删除
            MessageRec messageRec=messageRecDao.findOne(MessageRec.builder().recId(WebContext.getUserId()).messageId(delDTO.getMessageId()).build());
            //count = messageRecDao.deleteByField(MessageRec.builder().recId(WebContext.getUserId()).messageId(delDTO.getMessageId()).build());
            messageRecDao.updateByExample(MessageRec.builder().isDelete(1).build(),List.of(messageRec.getId()));
        }

        return new LinkedInfo(count+"");
    }
}

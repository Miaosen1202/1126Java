package com.wdcloud.lms.business.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_ONESUBJECTSENTREC
)
public class MessageSentSubjectQuery implements ISelfDefinedSearch<PageQueryResult> {
    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    MessageRecDataQuery messageRecDataQuery;
    /**
     * @api {get} /message/oneSubjectSentRec/query 某一主题下我的 已发件|已收件 消息列表
     * @apiName messageOneSubjectSentRecPageList
     * @apiGroup message
     * @apiParam {Number} subjectId 消息主题ID
     * @apiParam {Number} msgType 消息类型 0：已发件 1:已收件
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 列表
     * @apiSuccess {Number} entity.list.messageId 消息ID
     * @apiSuccess {Number} entity.list.sendId 发件人ID
     * @apiSuccess {String} entity.list.sendUsername 发件人用户名
     * @apiSuccess {String} entity.list.sendAvatarFileId 发件人头像
     * @apiSuccess {Number} entity.list.createTime 消息发送时间
     * @apiSuccess {String} entity.list.messageText 消息内容
     * @apiSuccess {Object[]} entity.list.recList 收件人集合
     * @apiSuccess {Number} entity.list.recList.id 收件人ID
     * @apiSuccess {String} entity.list.recList.username 收件人用户名
     */
    @Override
    public PageQueryResult search(Map<String, String> condition)  {
        //参数接收
        Integer subjectId = Integer.valueOf(condition.get("subjectId"));
        Integer pageIndex = Integer.valueOf(condition.get("pageIndex"));
        Integer pageSize = Integer.valueOf(condition.get("pageSize"));
        Integer msgType = Integer.valueOf(condition.get("msgType"));

        //PageHelper.startPage(pageIndex,pageSize);
        List<MsgItemVO> list = new ArrayList<>();
        if (msgType.equals(Status.NO.getStatus())) { //发件箱   我的已发送消息列表
            list =  messageSendDao.findSentMsgListBySubject(condition);
        }else{//收件箱   我的收件消息列表
            list = messageRecDao.findRecMsgListBySubject(condition);
            list.forEach(msgItemVO -> {
                messageRecDataQuery.buildIsPrivate(msgItemVO);
            });
            //此主题下收件箱消息全部置成已读
            isRead(subjectId);
        }
        int offset = pageSize * (pageIndex - 1);
        List<MsgItemVO> list1 = list.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
        return new PageQueryResult(list.size(), list1, pageSize, pageIndex);
    }

    private void isRead(Integer subjectId) {
        Example example = messageRecDao.getExample();
        example.createCriteria()
                .andEqualTo(MessageRec.REC_ID, WebContext.getUserId())
                .andEqualTo(MessageRec.SUBJECT_ID, subjectId);
        List<MessageRec> messageRecList=messageRecDao.find(example);
        List<Long> ids = messageRecList.stream().filter(o->o.getIsRead().equals(0)).map(MessageRec::getId).collect(Collectors.toList());
        if (ListUtils.isNotEmpty(ids)) {
            messageRecDao.updateByExample(MessageRec.builder().isRead(1).build(),ids);
        }
    }

}

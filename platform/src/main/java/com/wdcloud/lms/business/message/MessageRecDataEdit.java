package com.wdcloud.lms.business.message;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.message.dto.MessageRecReadDTO;
import com.wdcloud.lms.business.message.dto.MessageSendDTO;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.model.MessageSend;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE_REC)
public class MessageRecDataEdit implements IDataEditComponent {

    @Autowired
    private MessageRecDao messageRecDao;


    @Override
    @ValidationParam(clazz = MessageSendDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        MessageSendDTO dto = JSON.parseObject(dataEditInfo.beanJson, MessageSendDTO.class);
        return null;
    }

    /**
     * @api {post} /messageRec/modify 已读未读状态切换（包括批量）
     * @apiName messageRecModify
     * @apiGroup message
     * @apiParam {Object[]} messageIds 消息ID集合
     * @apiParam {Number=0,1} status 已读未读状态 1:置成已读 0:置成未读
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity]
     */
    @Override
    @ValidationParam(clazz = MessageRecReadDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        MessageRecReadDTO dto = JSON.parseObject(dataEditInfo.beanJson, MessageRecReadDTO.class);
        if (ListUtils.isNotEmpty(dto.getMessageIds())) {

            Example example = messageRecDao.getExample();
            example.createCriteria()
                    .andEqualTo(MessageRec.REC_ID, WebContext.getUserId())
                    .andIn(MessageRec.MESSAGE_ID, dto.getMessageIds());
            List<MessageRec> messageRecList=messageRecDao.find(example);
            List<Long> ids = messageRecList.stream().map(MessageRec::getId).collect(Collectors.toList());

            if (dto.getStatus().equals(Status.YES.getStatus())) {
                //切换为已读 主题下所有消息都置成已读
                List<Long> subjectIds = messageRecList.stream().map(MessageRec::getSubjectId).collect(Collectors.toList());
                Example example1 = messageRecDao.getExample();
                example1.createCriteria()
                        .andEqualTo(MessageRec.REC_ID, WebContext.getUserId())
                        .andIn(MessageRec.SUBJECT_ID, subjectIds);
                List<MessageRec> messageRecList1=messageRecDao.find(example1);
                ids = messageRecList1.stream().filter(o->o.getIsRead().equals(0)).map(MessageRec::getId).collect(Collectors.toList());
            }
            if (ListUtils.isNotEmpty(ids)) {
                messageRecDao.updateByExample(MessageRec.builder().isRead(dto.getStatus()).build(),ids);
            }
        }
        return new LinkedInfo(JSON.toJSONString(dto.getMessageIds()));
    }

    /**
     * @api {post} /messageRec/deletes 批量删除我的收件主题消息
     * @apiName messageRecDeletes
     * @apiGroup message
     * @apiParam {Number[]} ids 主题ID集合
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除成功总数
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "3"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> subjectIdList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        if (ListUtils.isEmpty(subjectIdList)) {
            throw new ParamErrorException();
        }
        Example example = messageRecDao.getExample();
        example.createCriteria()
                .andEqualTo(MessageRec.REC_ID, WebContext.getUserId())
                .andEqualTo(MessageRec.IS_DELETE, Status.NO.getStatus())
                .andIn(MessageRec.SUBJECT_ID, subjectIdList);
        //查询我的收件消息关系表
        List<MessageRec> delList = messageRecDao.find(example);
        List<Long> idList = delList.stream().map(MessageRec::getId).collect(Collectors.toList());
        //逻辑删除我的收件消息关系表
        int count = messageRecDao.updateByExample( MessageRec.builder().isDelete(Status.YES.getStatus()).build(), idList);
       // int count=messageRecDao.deletes(idList);
        return new LinkedInfo(count+"");
    }
}

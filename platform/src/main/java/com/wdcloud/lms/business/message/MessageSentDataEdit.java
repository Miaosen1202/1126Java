package com.wdcloud.lms.business.message;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.MessageSend;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE_SENT)
public class MessageSentDataEdit implements IDataEditComponent {

    @Autowired
    private MessageSendDao messageSendDao;

    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        return null;
    }

    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        return null;
    }

    /**
     * @api {post} /messageSent/deletes 我的已发送主题消息[批量]删除
     * @apiName messageSentDeletes
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
        Example example = messageSendDao.getExample();
        example.createCriteria()
                .andEqualTo(MessageSend.SEND_ID, WebContext.getUserId())
                .andEqualTo(MessageSend.IS_DELETE, Status.NO.getStatus())
                .andIn(MessageSend.SUBJECT_ID, subjectIdList);
        //查询我的已发送消息关系表
        List<MessageSend> delList = messageSendDao.find(example);
        List<Long> idList = delList.stream().map(MessageSend::getId).collect(Collectors.toList());
        //逻辑删除我的已发送消息关系表
         int count = messageSendDao.updateByExample( MessageSend.builder().isDelete(Status.YES.getStatus()).build(), idList);
        //int count = messageSendDao.deletes(idList);
        return new LinkedInfo(count+"");
    }
}

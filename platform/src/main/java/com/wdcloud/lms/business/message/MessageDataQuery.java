package com.wdcloud.lms.business.message;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.MessageDao;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE)
public class MessageDataQuery implements IDataQueryComponent<MsgItemVO> {

    @Autowired
    private MessageDao messageDao;

    /**
     * @api {get} /message/get 通过消息ID获取消息详情
     * @apiName messageGet
     * @apiGroup message
     * @apiParam {Number} data 消息messageId
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.topic 标题
     */
    @Override
    public MsgItemVO find(String id) {
        Long messageId = Long.valueOf(id);
        List<MsgItemVO> msgItemVOs=messageDao.findMsgItemByMsgIds(Set.of(messageId));
        if (ListUtils.isNotEmpty(msgItemVOs)) {
            return msgItemVOs.get(0);
        }
        return null;
    }
}

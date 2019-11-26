package com.wdcloud.lms.handle;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.mq.handler.IMqMessageHandler;
import com.wdcloud.mq.handler.MQHandler;
import com.wdcloud.mq.model.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 班级学生增加、删除、移动消息处理:
 *
 */
@Slf4j
@MQHandler(
        exchangeName = MqConstants.TOPIC_EXCHANGE_USER_SECTION_OPRATE,
        queueName = MqConstants.QUEUE_USER_SECTION_OPRATE
)
public class AssignUserOparateHandler implements IMqMessageHandler {
    @Autowired
    private AssignUserService assignUserService;


    @Override
    public void processMsg(Object e) {
        if (e instanceof AssignUserOparateDTO) {
            AssignUserOparateDTO assignUserOparateDTO = (AssignUserOparateDTO) e;

            log.debug("[AssignUserOparateHandler] receive a user section oparate msg, msg={}", JSON.toJSONString(e));
           assignUserService.assigUserOparate(assignUserOparateDTO);

        }
    }
}

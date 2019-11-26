package com.wdcloud.lms.handle;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.mapper.OperationRecordMapper;
import com.wdcloud.lms.core.base.model.OperationRecord;
import com.wdcloud.mq.handler.IMqMessageHandler;
import com.wdcloud.mq.handler.MQHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 课程发布消息处理:
 * 1. 查询course_user_join_pending记录表,获取加入课程尚未邀请的用户,给用户发送邀请邮件
 */
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@MQHandler(
        exchangeName = Constants.MQ_OPERATION_LOG,
        queueName = Constants.MQ_LOG
)
public class OperationLogHandler implements IMqMessageHandler {
    @Autowired
    private OperationRecordMapper operationRecordMapper;

    @Override
    public void processMsg(Object o) {
        if (o instanceof OperationRecord) {
            try {
                operationRecordMapper.insert((OperationRecord) o);
            } catch (Exception ignored) {

            }
        }
    }
}

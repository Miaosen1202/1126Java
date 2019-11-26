package com.wdcloud.lms.base.service;

import com.wdcloud.mq.core.IMessageSendService;
import com.wdcloud.mq.core.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(MqConfig.class)
public class MqSendService {
    @Autowired
    private IMessageSendService messageSendService;

    public void sendMessage(String exchange, Object message) {
        messageSendService.send(exchange, message);
    }

    public void sendMessage(String queueName, String exchange, Object message) {
        messageSendService.send(queueName, exchange, message);
    }

    public void sendMessage(String exchange, String queueName, byte[] message) {
        messageSendService.send(queueName, exchange, message);
    }

    public void setMessageSendService(IMessageSendService messageSendService) {
        this.messageSendService = messageSendService;
    }
}

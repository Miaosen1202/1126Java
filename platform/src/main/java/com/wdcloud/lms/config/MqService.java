package com.wdcloud.lms.config;

import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.handle.CourseUserJoinedHandler;
import com.wdcloud.mq.core.DefaultEventControllerImpl;
import com.wdcloud.mq.core.IMessageSendService;
import com.wdcloud.mq.core.MqConfig;
import com.wdcloud.mq.handler.IMqMessageHandler;
import com.wdcloud.mq.handler.MQHandler;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.utils.AnnotationUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(MqConfig.class)
public class MqService {
    @Autowired
    private MqConfig mqConfig;
    private IMessageSendService messageSendService;

    @Autowired(required = false)
    private IMqMessageHandler[] messageHandlers;

    @PostConstruct
    public void init() {
        DefaultEventControllerImpl controller = DefaultEventControllerImpl.getInstance(mqConfig);
        this.messageSendService = controller.getEopEventTemplate();

        if (messageHandlers != null && messageHandlers.length > 0) {
            for (IMqMessageHandler messageHandler : messageHandlers) {
                MQHandler mqHandler = AnnotationUtils.getAnnotation(messageHandler, MQHandler.class);
                if (StringUtil.isNotEmpty(mqHandler.queueName())) {
                    controller.add(mqHandler.queueName(), mqHandler.exchangeName(), messageHandler, mqHandler.isFanout());
                }
            }
        }

        controller.start();
    }

    @Bean
    public IMessageSendService messageSendService() {
        return messageSendService;
    }
}

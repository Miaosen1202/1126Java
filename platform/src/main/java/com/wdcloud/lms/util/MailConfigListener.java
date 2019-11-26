package com.wdcloud.lms.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author wangff
 */
@Slf4j
@Component
public class MailConfigListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof MailConfigEvent) {
            MailService mailService = (MailService) applicationEvent.getSource();
            mailService.initJavaMailSenderImplMap();
        }
    }
}

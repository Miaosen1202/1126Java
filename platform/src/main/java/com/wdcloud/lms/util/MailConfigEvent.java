package com.wdcloud.lms.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author wangff
 */
@Slf4j
public class MailConfigEvent extends ApplicationEvent {

    public MailConfigEvent(Object source) {
        super(source);
    }

}

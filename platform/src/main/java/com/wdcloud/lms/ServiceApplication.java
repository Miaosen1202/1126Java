package com.wdcloud.lms;

import com.wdcloud.lms.util.MailConfigEvent;
import com.wdcloud.lms.util.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication(exclude={MailSenderAutoConfiguration.class})
@EnableScheduling
@EnableAsync
public class ServiceApplication implements CommandLineRunner {
    public static void main(String[] args) {
        ApplicationContext context= SpringApplication.run(ServiceApplication.class);
        context.publishEvent(new MailConfigEvent(context.getBean("mailService")));
    }

    @Override
    public void run(String... args) {
        log.info("ServiceApplication started");
    }
}

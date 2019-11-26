package com.wdcloud.lms.util;


import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.OrgEmailDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.OrgEmail;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.util.enums.EmailSecurityTypeEnum;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

/**
 *
 * @author wangff
 */
@Slf4j
@Component
public class MailService {

    public static Map<String, JavaMailSenderImpl> javaMailSenderMap = new HashMap<>();

    @Autowired
    private OrgEmailDao orgEmailDao;
    @Autowired
    private UserDao userDao;

//    @PostConstruct
    public  void initJavaMailSenderImplMap(){
        //查库初始化 组装 跟机构--邮件服务器 映射关系
        Example example = orgEmailDao.getExample();
        example.createCriteria().andGreaterThan(OrgEmail.ID,0);
        List<OrgEmail> orgEmailList= orgEmailDao.find(example);
        if (ListUtils.isNotEmpty(orgEmailList)) {
            orgEmailList.forEach(orgEmail -> {
                JavaMailSenderImpl sender = new JavaMailSenderImpl();
                sender.setProtocol("smtp");
                sender.setDefaultEncoding("utf-8");

                sender.setHost(orgEmail.getEmailHost());
                sender.setPort(orgEmail.getEmaiPort());
                sender.setUsername(orgEmail.getEmailUserName());
                sender.setPassword(orgEmail.getEmailPwd());

                Properties p = new Properties();
                p.setProperty("mail.display.sendname",orgEmail.getEmailDisplayName()+" <"+orgEmail.getEmailUserName()+">");

                if (EmailSecurityTypeEnum.SSL.getValue().equals(orgEmail.getEmaiSecurityType())) {
                    p.setProperty("mail.smtp.ssl.enable","true");
                }
                p.setProperty("mail.smtp.auth","true");
                p.setProperty("mail.smtp.starttls.enable","true");
                p.setProperty("mail.smtp.starttls.required","true");

                sender.setJavaMailProperties(p);
                javaMailSenderMap.put(orgEmail.getRootOrgCode(),sender);
            });
            log.info("Init mail ok...**************number:{}",javaMailSenderMap.size());
        }
    }

    /**
     * The Template engine.
     */
    @Autowired
    TemplateEngine templateEngine;

    /**
     * Send simple email.
     *
     * @param recEmail the receiver 收件邮箱
     * @param subject  the subject
     * @param content  the content
     * @throws Exception the service exception
     */
//    @Async
    public void sendSimpleEmail(String recEmail, String subject, String content)  {
        String[] recEmails = {recEmail};
        sendSimpleEmail(getJavaMailSenderImpl(recEmail), recEmails, null, subject, content);
    }

    /**
     *   1.获取收件邮箱跟机构
     *   2.通过根机构 得到 发件账号，服务器
     * @param recEmail
     * @return
     */
    private JavaMailSenderImpl getJavaMailSenderImpl(String recEmail) {
        String rootOrgCode = getRootOrgCodeByRecEmail(recEmail);
        return javaMailSenderMap.get(rootOrgCode);
    }

    /**
     * 通过收件箱获取跟机构代码
     * @param recEmail
     * @return
     */
    private String getRootOrgCodeByRecEmail(String recEmail) {
        User user=userDao.findOne(User.builder().email(recEmail).build());
        if (user == null) {
             user=WebContext.getUser();
        }
        return user.getOrgTreeId().substring(0,8);
    }

    /**
     * Send simple email.
     *
     * @param javaMailSender    the deliver
     * @param recEmails   the receiver
     * @param carbonCopy the carbon copy
     * @param subject    the subject
     * @param content    the content
     * @throws Exception the service exception
     */
    public void sendSimpleEmail(JavaMailSenderImpl javaMailSender, String[] recEmails, String[] carbonCopy, String subject, String content)  {

        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(javaMailSender.getJavaMailProperties().getProperty("mail.display.sendname"));
            message.setTo(recEmails);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            log.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
           // throw new Exception(e.getMessage());
        }
    }

    /**
     * Send html email.
     *
     * @param receiver the receiver
     * @param subject  the subject
     * @param content  the content
     * @throws Exception the service exception
     */
//    @Async
    public void sendHtmlEmail(String receiver, String subject, String content) {
        String[] receivers = {receiver};
        sendHtmlEmail(getJavaMailSenderImpl(receiver), receivers, null, subject, content, true);
    }

    /**
     * Send html email.
     *
     * @param javaMailSender    the deliver
     * @param receiver   the receiver
     * @param carbonCopy the carbon copy
     * @param subject    the subject
     * @param content    the content
     * @param isHtml     the is html
     * @throws Exception the service exception
     */
    public void sendHtmlEmail(JavaMailSenderImpl javaMailSender, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml)  {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send email ...");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(javaMailSender.getJavaMailProperties().getProperty("mail.display.sendname"));
            messageHelper.setTo(receiver);
            if (carbonCopy != null) {
                messageHelper.setCc(carbonCopy);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            javaMailSender.send(message);
            log.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send email failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
           // throw new Exception(e.getMessage());
        }
    }

    /**
     * Send attachment file email.
     *
     * @param deliver  the deliver
     * @param receiver the receiver
     * @param subject  the subject
     * @param content  the content
     * @param fileName the file name
     * @param file     the file
     * @throws Exception the service exception
     */
    public void sendAttachmentFileEmail(String deliver, String receiver, String subject, String content, String fileName, File file) throws Exception {
        String[] receivers = {receiver};
        sendAttachmentFileEmail(getJavaMailSenderImpl(receiver), receivers, null, subject, content, true, fileName, file);
    }

    /**
     * Send attachment file mail.
     *
     * @param javaMailSender    the deliver
     * @param receiver   the receiver
     * @param carbonCopy the carbon copy
     * @param subject    the subject
     * @param content    the content
     * @param isHtml     the is html
     * @param fileName   the file name
     * @param file       the file
     * @throws Exception the service exception
     */
    public void sendAttachmentFileEmail(JavaMailSenderImpl javaMailSender, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ...");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(javaMailSender.getJavaMailProperties().getProperty("mail.display.sendname"));
            messageHelper.setTo(receiver);
//            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.addAttachment(fileName, file);

            javaMailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send mail failed, error message is {}\n", e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Send template email.
     *
     * @param receiver the receiver
     * @param subject  the subject
     * @param template the template
     * @param context  the context
     * @throws Exception the service exception
     */
    public void sendTemplateEmail(String receiver, String subject, String template, Context context) throws Exception {
        String[] receivers = {receiver};
        sendTemplateEmail(getJavaMailSenderImpl(receiver), receivers, null, subject, template, context);
    }

    /**
     * Send template mail.
     *
     * @param javaMailSender    the deliver
     * @param receiver   the receiver
     * @param carbonCopy the carbon copy
     * @param subject    the subject
     * @param template   the template
     * @param context    the context
     * @throws Exception the service exception
     */
   public void sendTemplateEmail(JavaMailSenderImpl javaMailSender, String[] receiver, String[] carbonCopy, String subject, String template, Context context) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ...");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            String content = templateEngine.process(template, context);
            messageHelper.setFrom(javaMailSender.getJavaMailProperties().getProperty("mail.display.sendname"));
            messageHelper.setTo(receiver);
            if (carbonCopy != null) {
                messageHelper.setCc(carbonCopy);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            javaMailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send mail failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}

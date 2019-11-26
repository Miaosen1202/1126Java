package com.wdcloud.lms.base.service;

import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserEmailDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.UserEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private UserEmailDao userEmailDao;

    /**
     * 发送邀请加入班级邮件
     * @param sectionUserId
     */
    public void sendInvite(Long sectionUserId) {
        SectionUser sectionUser = sectionUserDao.get(sectionUserId);
        if (sectionUser != null) {

            UserEmail userEmail = userEmailDao.findOne(UserEmail.builder().userId(sectionUser.getUserId()).isDefault(Status.YES.getStatus()).build());
            String email = userEmail.getEmail();
            // todo send email
            log.info("[EmailService] send course invite email to user, courseId={}, userId={}, email={}",
                    sectionUser.getCourseId(), sectionUser.getUserId(), email);
        }
    }

    public void sendInvite(Long courseId, Long userId) {
        SectionUser record = SectionUser.builder().courseId(courseId).userId(userId).registryStatus(UserRegistryStatusEnum.PENDING.getStatus()).build();
        List<SectionUser> sectionUsers = sectionUserDao.find(record);
        // todo send email
    }
}

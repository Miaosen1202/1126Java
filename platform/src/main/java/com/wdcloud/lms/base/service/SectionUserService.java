package com.wdcloud.lms.base.service;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.mq.model.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SectionUserService {

    @Autowired
    private AssignService assignService;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private MqSendService mqSendService;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    public void sendSectionUserChangeMsg(Long courseId, Long sectionId, Long userId, Long roleId,
                                         AssignUserOparateDTO.OperateType operateType,
                                         Long moveTargetSectionId) {
        if (Objects.equals(RoleEnum.STUDENT.getType(), roleId)) {
            AssignUserOparateDTO dto = AssignUserOparateDTO.builder()
                    .courseId(courseId)
                    .userId(userId)
                    .srcSectionId(sectionId)
                    .destSectionId(moveTargetSectionId)
                    .oparateType(operateType.getType())
                    .build();
            mqSendService.sendMessage(MqConstants.QUEUE_USER_SECTION_OPRATE, MqConstants.TOPIC_EXCHANGE_USER_SECTION_OPRATE, dto);
            if (AssignUserOparateDTO.OperateType.ADD.equals(operateType) || AssignUserOparateDTO.OperateType.DELETE.equals(operateType)) {
                moduleCompleteService.addSectionUser(dto);
            }
            log.info("[SectionUserService] send section user change msg, msg={}", JSON.toJSONString(dto));
        }
    }

    public int deleteBySectionIds(Collection<Long> sectionIds) {
        Example sectionUserExample = sectionUserDao.getExample();
        sectionUserExample.createCriteria()
                .andIn(SectionUser.SECTION_ID, sectionIds);

        List<SectionUser> sectionUsers = sectionUserDao.find(sectionUserExample);
        for (SectionUser sectionUser : sectionUsers) {
            delete(sectionUser);
        }

        return sectionUsers.size();
    }
    public int deleteByCourseIds(Collection<Long> courseIds) {
        Example sectionUserExample = sectionUserDao.getExample();
        sectionUserExample.createCriteria()
                .andIn(SectionUser.COURSE_ID, courseIds);

        List<SectionUser> sectionUsers = sectionUserDao.find(sectionUserExample);
        for (SectionUser sectionUser : sectionUsers) {
            delete(sectionUser);
        }

        return sectionUsers.size();
    }

    public int deleteByUserIds(Collection<Long> userIds) {
        Example sectionUserExample = sectionUserDao.getExample();
        sectionUserExample.createCriteria()
                .andIn(SectionUser.USER_ID, userIds);
        List<SectionUser> sectionUsers = sectionUserDao.find(sectionUserExample);
        for (SectionUser sectionUser : sectionUsers) {
            delete(sectionUser);
        }

        return sectionUsers.size();
    }

    public void delete(Long sectionId, Long userId, Long roleId) {
        Example example = sectionUserDao.getExample();
        example.createCriteria()
                .andEqualTo(SectionUser.SECTION_ID, sectionId)
                .andEqualTo(SectionUser.USER_ID, userId)
                .andEqualTo(SectionUser.ROLE_ID, roleId);
        List<SectionUser> sectionUsers = sectionUserDao.find(example);
        for (SectionUser sectionUser : sectionUsers) {
            delete(sectionUser);
        }
    }

    public void delete(Long courseId, Long sectionId, Long userId, Long roleId) {
        delete(courseId, sectionId, userId, roleId, null);
    }
    public void delete(Long courseId, Long sectionId, Long userId, Long roleId, Integer registryStatus) {
        int i = sectionUserDao.deleteByField(SectionUser.builder()
                .courseId(courseId)
                .sectionId(sectionId)
                .userId(userId)
                .roleId(roleId)
                .registryStatus(registryStatus)
                .build());
        if (i > 0) {
            sendSectionUserChangeMsg(courseId, sectionId,
                    userId, roleId, AssignUserOparateDTO.OperateType.DELETE, null);
        }
    }


    public void delete(SectionUser sectionUser) {
        int delete = sectionUserDao.delete(sectionUser.getId());
        if (delete > 0) {
            sendSectionUserChangeMsg(sectionUser.getCourseId(), sectionUser.getSectionId(),
                    sectionUser.getUserId(), sectionUser.getRoleId(), AssignUserOparateDTO.OperateType.DELETE, null);
        }

    }

    public void save(SectionUser sectionUser) {
        sectionUserDao.save(sectionUser);

        sendSectionUserChangeMsg(sectionUser.getCourseId(), sectionUser.getSectionId(), sectionUser.getUserId(),
                sectionUser.getRoleId(), AssignUserOparateDTO.OperateType.ADD, null);
    }
}

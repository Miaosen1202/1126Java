package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.mq.core.IMessageSendService;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.PasswordUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

/**
 * 完善临时用户信息
 */
public class CourseUserInfoImprove implements ISelfDefinedEdit {

    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/improve/edit 临时用户信息完善
     * @apiDescription 将临时用户注册称正式用户
     * @apiName courseUserImproveEdit
     * @apiGroup People
     *
     * @apiParam {Number} code 邀请code
     * @apiParam {Number} password 密码
     * @apiParam {Number} [language] 语言
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {String} entity 用户ID
     */
    @ValidationParam(clazz = UserImproveVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserImproveVo userImproveVo = JSON.parseObject(dataEditInfo.beanJson, UserImproveVo.class);

        CourseUserJoinPending joinPending = courseUserJoinPendingDao.getByCode(userImproveVo.getCode());
        if (joinPending == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_CODE, joinPending.getCode());
        }

        User user = userDao.get(joinPending.getUserId());
        if (user == null) {
            throw new PermissionException();
        }

        user.setPassword(PasswordUtil.haxPassword(userImproveVo.getPassword()));
        user.setIsRegistering(Status.NO.getStatus());
        user.setStatus(Status.YES.getStatus());
        user.setLanguage(userImproveVo.getLanguage());
        userDao.save(user);
        // todo 发送添加用户消息,完善用户其他关联信息

        SectionUser sectionUser = sectionUserDao.findOne(SectionUser.builder()
                .userId(joinPending.getUserId())
                .courseId(joinPending.getCourseId())
                .sectionId(joinPending.getSectionId())
                .roleId(joinPending.getRoleId())
                .build());
        sectionUser.setRegistryStatus(UserRegistryStatusEnum.JOINED.getStatus());
        sectionUserService.save(sectionUser);

        return new LinkedInfo(String.valueOf(user.getId()));
    }

    @Data
    public static class UserImproveVo {
        @NotBlank
        private String code;
        @NotBlank
        private String password;
        private String language;
    }
}

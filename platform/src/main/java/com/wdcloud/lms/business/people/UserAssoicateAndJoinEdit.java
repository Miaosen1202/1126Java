package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * todo 管理课程用户到已有用户
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_ASSOCIATE_AND_JOIN
)
public class UserAssoicateAndJoinEdit implements ISelfDefinedEdit {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private UserEmailDao userEmailDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/associateAndJoin/edit
     * @apiName CourseUserAssociateAndJoin
     * @apiGroup People
     *
     * @apiParam {String} code 课程班级邀请码
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 用户ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserAssAndJoinVo assAndJoinVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserAssAndJoinVo.class);
        CourseUserJoinPending joinPending = courseUserJoinPendingDao.getByCode(assAndJoinVo.getCode());

        if (joinPending == null) {
            throw new PropValueUnRegistryException("code", assAndJoinVo.getCode());
        }

        User user = userDao.get(joinPending.getUserId());
        if (user == null || Objects.equals(user.getIsRegistering(), Status.NO.getStatus())) {
            throw new PermissionException();
        }

        // 添加临时用户邮箱到当前用户
        UserEmail userEmail = UserEmail.builder()
                .userId(WebContext.getUserId())
                .email(user.getEmail())
                .isDefault(Status.NO.getStatus())
                .build();
        userEmailDao.save(userEmail);

        // 更新当前用户到班级用户中
        SectionUser sectionUserRecord = SectionUser.builder()
                .userId(user.getId())
                .courseId(joinPending.getCourseId())
                .sectionId(joinPending.getSectionId())
                .roleId(joinPending.getRoleId())
                .build();
        SectionUser sectionUser = sectionUserDao.findOne(sectionUserRecord);
        sectionUser.setUserId(WebContext.getUserId());
        sectionUser.setRegistryStatus(UserRegistryStatusEnum.JOINED.getStatus());
        sectionUserService.save(sectionUser);

        // 更新临时用户其他邀请到当前用户
        sectionUserDao.updateSectionUserUserId(user.getId(), WebContext.getUserId(), joinPending.getCourseId(),
                joinPending.getSectionId(), WebContext.getUserId());
        courseUserJoinPendingDao.updateUserInviteToNewUser(user.getId(), WebContext.getUserId());

        // 清理临时用户数据
        userDao.delete(joinPending.getUserId());
        courseUserDao.deleteByField(CourseUser.builder().userId(user.getId()).courseId(joinPending.getCourseId()).build());

        return new LinkedInfo(String.valueOf(WebContext.getUserId()));
    }

    @Data
    public static class CourseUserAssAndJoinVo {
        @NotBlank
        private String code;
    }
}

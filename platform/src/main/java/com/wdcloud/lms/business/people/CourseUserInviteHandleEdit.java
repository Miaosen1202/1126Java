package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_INVITE_HANDLE
)
public class CourseUserInviteHandleEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     *
     * @api {post} /courseUser/inviteHandle/edit
     * @apiDescription 用户同意或拒绝课程加入邀请
     * @apiName CourseUserInviteHandle
     * @apiGroup People
     *
     * @apiParam {Number} code 邀请码
     *
     * @apiSuccess {String} code 状态码
     * @apiSuccess {String} [message] 状态信息
     * @apiSuccess {String} [entity] 用户ID
     */
    @ValidationParam(clazz = CourseUserInviteHandleVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserInviteHandleVo inviteHandleVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserInviteHandleVo.class);
        CourseUserJoinPending joinPending = courseUserJoinPendingDao.getByCode(inviteHandleVo.getCode());
        if (joinPending == null || !Objects.equals(WebContext.getUserId(), joinPending.getUserId())) {
            throw new PermissionException();
        }

        Status isAccept = Status.statusOf(inviteHandleVo.getIsAccept());
        if (isAccept == Status.YES) {
            sectionUserDao.updateSectionUserJoinStatus(joinPending.getCourseId(), joinPending.getSectionId(),
                    joinPending.getUserId(), joinPending.getRoleId(), UserRegistryStatusEnum.JOINED, WebContext.getUserId());
        } else {
            log.info("[CourseUserInviteHandleEdit] user is deny course invite, userId={}, courseId={}, sectionId={}, roleId={}",
                    WebContext.getUserId(), joinPending.getCourseId(), joinPending.getSectionId(), joinPending.getRoleId());
            sectionUserService.delete(joinPending.getCourseId(), joinPending.getSectionId(), joinPending.getUserId(),
                    joinPending.getRoleId(), UserRegistryStatusEnum.PENDING.getStatus());
        }
        courseUserJoinPendingDao.delete(joinPending.getId());

        return new LinkedInfo(String.valueOf(joinPending.getUserId()));
    }

    @Data
    public static class CourseUserInviteHandleVo {
        @NotBlank
        private String code;
        @NotNull
        @Range(min = 0, max = 1)
        private Integer isAccept;
    }
}

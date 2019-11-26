package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.EmailService;
import com.wdcloud.lms.business.people.vo.ResendCourseInviteVo;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 向用户重新发送邀请邮件
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_RESEND_INVITE
)
public class ResendCourseInvitingEdit implements ISelfDefinedEdit {

    @Autowired
    private EmailService emailService;

    /**
     * @api {post} /courseUser/resendInvite 重新给用户发送邀请信息
     * @apiName CourseUserResendInvite
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {String} [entity] 用户ID
     *
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        ResendCourseInviteVo resendCourseInviteVo = JSON.parseObject(dataEditInfo.beanJson, ResendCourseInviteVo.class);
        if (Objects.equals(WebContext.getUserId(), resendCourseInviteVo.getUserId())) {
            throw new PermissionException();
        }

        emailService.sendInvite(resendCourseInviteVo.getCourseId(), resendCourseInviteVo.getUserId());
        return new LinkedInfo(String.valueOf(resendCourseInviteVo.getUserId()));
    }
}

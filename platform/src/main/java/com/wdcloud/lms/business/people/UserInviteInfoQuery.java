package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 用户邀请信息查询接口
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_INVITE
)
public class UserInviteInfoQuery implements ISelfDefinedSearch<UserInviteInfoQuery.InviteInfo> {
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserDao sectionUserDao;

    /**
     * @api {post} /courseUser/inviteInfo/query 用户邀请
     * @apiDescription 通过邮箱点击跳转链接
     * @apiName courseUserInviteQuery
     * @apiGroup People
     *
     * @apiParam {Number} code 邀请code
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {Object} entity 邀请信息
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {Number} entity.registryStatus 注册状态
     * @apiSuccess {Number} entity.isRegistering 是否临时用户
     */
    @Override
    public InviteInfo search(Map<String, String> condition) {
        if (!condition.containsKey(Constants.PARAM_CODE) || StringUtil.isEmpty(condition.get(Constants.PARAM_CODE))) {
            throw new ParamErrorException();
        }

        String code = condition.get(Constants.PARAM_CODE);
        CourseUserJoinPending joinPending = courseUserJoinPendingDao.getByCode(code);
        if (joinPending == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_CODE, code);
        }

        User user = userDao.get(joinPending.getUserId());
        if (user == null) {
            throw new PermissionException();
        }

        SectionUser sectionUser = sectionUserDao.findOne(SectionUser.builder().courseId(joinPending.getCourseId()).sectionId(joinPending.getSectionId()).userId(joinPending.getUserId()).roleId(joinPending.getRoleId()).build());
        if (sectionUser == null) {
            throw new PermissionException();
        }

        InviteInfo inviteInfo = InviteInfo.builder()
                .userId(joinPending.getUserId())
                .registryStatus(sectionUser.getRegistryStatus())
                .isRegistering(user.getIsRegistering())
                .build();
        return inviteInfo;
    }

    @Data
    @Builder
    public static class InviteInfo {
        private Long userId;
        private Integer registryStatus;
        private Integer isRegistering;
    }
}

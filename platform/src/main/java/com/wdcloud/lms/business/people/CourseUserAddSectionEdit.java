package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.business.people.vo.CourseUserAddSectionVo;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.handle.dto.UserJoinedCourseDto;
import com.wdcloud.mq.core.IMessageSendService;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_SECTION_ADD
)
public class CourseUserAddSectionEdit implements ISelfDefinedEdit {
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MqSendService mqSendService;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/sectionAdd/edit
     * @apiDescription 将用户添加到班级,角色为学生
     * @apiName courseUserSectionAdd
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     * @apiParam {Number} sectionId 班级
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 用户ID
     *
     */
    @ValidationParam(clazz = CourseUserAddSectionVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserAddSectionVo courseUserAddSectionVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserAddSectionVo.class);

        Long courseId = courseUserAddSectionVo.getCourseId();
        Long userId = courseUserAddSectionVo.getUserId();
        CourseUser courseUser = courseUserDao.findOne(CourseUser.builder().courseId(courseId).userId(userId).build());
        if (courseUser == null) {
            throw new PermissionException();
        }

        Long roleId;
        Long sectionId = courseUserAddSectionVo.getSectionId();
        if (Objects.equals(WebContext.getUserId(), userId)) {
            roleId = roleService.getTeacherRoleId();
            SectionUser sectionUser = SectionUser.builder()
                    .courseId(courseId)
                    .userId(userId)
                    .sectionId(sectionId)
                    .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                    .registryOrigin(UserRegistryOriginEnum.SELF_ENROLL.getOrigin())
                    .roleId(roleId)
                    .build();
            sectionUserService.save(sectionUser);
        } else {
            roleId = roleService.getStudentRoleId();
            SectionUser sectionUser = SectionUser.builder()
                    .courseId(courseId)
                    .userId(userId)
                    .sectionId(sectionId)
                    .registryStatus(UserRegistryStatusEnum.PENDING.getStatus())
                    .registryOrigin(UserRegistryOriginEnum.INVITE.getOrigin())
                    .roleId(roleId)
                    .build();
            sectionUserService.save(sectionUser);

            UserJoinedCourseDto joinedCourseDto = UserJoinedCourseDto.builder()
                    .sectionUserId(sectionUser.getId())
                    .courseId(sectionUser.getCourseId())
                    .userId(sectionUser.getUserId())
                    .sectionId(sectionUser.getSectionId())
                    .roleId(sectionUser.getRoleId())
                    .build();
            mqSendService.sendMessage(MqConstants.QUEUE_USER_JOINED_COURSE, MqConstants.TOPIC_EXCHANGE_USER_JOINED_COURSE, joinedCourseDto);
        }

        return new LinkedInfo(String.valueOf(userId));
    }
}

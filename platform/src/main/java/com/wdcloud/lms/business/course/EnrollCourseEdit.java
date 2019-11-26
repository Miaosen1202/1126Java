package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.SystemErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.PasswordUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_ENROLL
)
public class EnrollCourseEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /course/enroll/edit 注册课程
     * @apiName CourseEnrollEdit
     * @apiGroup Course
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParam {String} code 注册码
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 消息描述
     */
    @ValidationParam(clazz = CourseEnrollDto.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseEnrollDto courseEnrollDto = JSON.parseObject(dataEditInfo.beanJson, CourseEnrollDto.class);

        CourseConfig courseConfig = courseConfigDao.findOne(CourseConfig.builder().openRegistryCode(courseEnrollDto.getCode()).build());
        if (courseConfig == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_CODE, courseEnrollDto.getCode());
        }
        Course course = courseDao.get(courseConfig.getCourseId());

        User user = userDao.findByUsername(courseEnrollDto.getUsername());
        if (user == null) {
            throw new BaseException("login.error");
        }
        if (!Objects.equals(PasswordUtil.haxPassword(courseEnrollDto.getPassword()), user.getPassword())) {
            throw new BaseException("login.error");
        }

        List<SectionUser> sectionUsers = sectionUserDao.find(SectionUser.builder()
                .courseId(course.getId())
                .userId(user.getId())
                .roleId(RoleEnum.STUDENT.getType())
                .build());

        boolean joined = false;
        for (SectionUser sectionUser : sectionUsers) {
            if (Objects.equals(sectionUser.getRegistryStatus(), UserRegistryStatusEnum.JOINED.getStatus())) {
                joined = true;
                break;
            }
        }

        if (joined) {
            return new LinkedInfo(String.valueOf(user.getId()));
        }

        if (ListUtils.isNotEmpty(sectionUsers)) {
            SectionUser sectionUser = sectionUsers.get(0);
            sectionUser.setRegistryStatus(UserRegistryStatusEnum.JOINED.getStatus());
            sectionUserDao.update(sectionUser);
            return new LinkedInfo(String.valueOf(user.getId()));
        }

        Section joinSection = null;
        List<Section> sections = sectionDao.find(Section.builder().courseId(course.getId()).build());
        for (Section section : sections) {
            if (Objects.equals(section.getIsDefault(), Status.YES.getStatus())) {
                joinSection = section;
                break;
            }
        }

        if (joinSection == null) {
            joinSection = ListUtils.isNotEmpty(sections) ? sections.get(0) : null;
        }

        if (joinSection == null) {
            throw new SystemErrorException();
        }

        if (Objects.equals(course.getIsConcluded(), Status.YES.getStatus())
                || Objects.equals(course.getStatus(), Status.NO.getStatus())
                || Objects.equals(course.getIsDeleted(), Status.YES.getStatus())) {
            throw new PermissionException();
        }

        if (!courseUserDao.hasUser(course.getId(), user.getId())) {
            CourseUser courseUser = CourseUser.builder()
                    .courseId(course.getId())
                    .userId(user.getId())
                    .isActive(Status.YES.getStatus())
                    .createUserId(user.getId())
                    .updateUserId(user.getId())
                    .build();
            courseUserDao.save(courseUser);
        }

        SectionUser sectionUser = SectionUser.builder()
                .courseId(course.getId())
                .sectionId(joinSection.getId())
                .userId(user.getId())
                .roleId(RoleEnum.STUDENT.getType())
                .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                .registryOrigin(UserRegistryOriginEnum.SELF_ENROLL.getOrigin())
                .createUserId(user.getId())
                .updateUserId(user.getId())
                .build();
        sectionUserService.save(sectionUser);

        return new LinkedInfo(String.valueOf(user.getId()));
    }

    @Data
    public static class CourseEnrollDto {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String code;
    }
}

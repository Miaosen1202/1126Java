package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_PUBLIC_REGISTRY
)
public class PublicCourseRegistryEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /course/publicRegistry/edit 加入公共课程
     * @apiDescription 加入公共课程
     * @apiName coursePublicRegistryEdit
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 课程ID
     *
     */
    @ValidationParam(clazz = JoinPublicCourseVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        JoinPublicCourseVo joinPublicCourseVo = JSON.parseObject(dataEditInfo.beanJson, JoinPublicCourseVo.class);

        Course course = courseDao.get(joinPublicCourseVo.getCourseId());

        if (course == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_COURSE_ID, joinPublicCourseVo.getCourseId());
        }

        if (Objects.equals(course.getIncludePublishIndex(), Status.NO.getStatus())) {
            throw new PermissionException();
        }

        Long userId = WebContext.getUserId();
        boolean hasStudentUser = sectionUserDao.count(SectionUser.builder()
                .courseId(course.getId())
                .userId(userId)
                .roleId(RoleEnum.STUDENT.getType())
                .build()) > 0;
        if (hasStudentUser) {
            return new LinkedInfo(String.valueOf(course.getId()));
        }

        if (!courseUserDao.hasUser(course.getId(), userId)) {
            CourseUser courseUser = CourseUser.builder()
                    .courseId(course.getId())
                    .userId(userId)
                    .isActive(Status.YES.getStatus())
                    .isFavorite(Status.YES.getStatus())
                    .build();
            courseUserDao.save(courseUser);

            log.info("[PublicCourseRegistry] add user to course user, courseId={}, userId={}", course.getId(), userId);
        }

        Section defaultSection = sectionDao.findOne(Section.builder().courseId(course.getId()).isDefault(Status.YES.getStatus()).build());
        SectionUser sectionUser = SectionUser.builder()
                .courseId(course.getId())
                .userId(userId)
                .roleId(RoleEnum.STUDENT.getType())
                .sectionId(defaultSection.getId())
                .registryOrigin(UserRegistryOriginEnum.SELF_ENROLL.getOrigin())
                .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                .build();
        sectionUserService.save(sectionUser);

        log.info("[PublicCourseRegistry] add user to default section, courseId={}, sectionId={}, userId={}",
                course.getId(), defaultSection.getId(), userId);

        return new LinkedInfo(String.valueOf(course.getId()));
    }

    @Data
    public static class JoinPublicCourseVo {
        @NotNull
        private Long courseId;
    }
}

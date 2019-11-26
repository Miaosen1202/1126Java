package com.wdcloud.lms.business.section;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.business.course.vo.CourseAddVo;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 新建课程后关联创建默认班级，默认班级与课程同名
 * 创建默认班级时，将创建课程的教师添加到班级中，角色为教师
 */
@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class SectionAfterCourseAddLinkHandle implements IDataLinkedHandle {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SectionUserService sectionUserService;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        CourseAddVo course = JSON.parseObject(linkedInfo.beanJson, CourseAddVo.class);
        String name = course.getName();
        String alias = course.getAlias();

        Section section = Section.builder().isDefault(Status.YES.getStatus()).name(name).courseId(courseId).build();
        sectionDao.save(section);
        log.info("[SectionAfterCourseAddLinkHandle] add course default section, courseId={}, sectionId={}, name={}",
                courseId, section.getId(), section.getName());
        if (roleService.isAdmin()) {
            //管理员创建的课程 不必将自己设置为课程教师
            return linkedInfo;
        }
        SectionUser sectionUser = SectionUser.builder()
                .courseId(courseId)
                .userId(WebContext.getUserId())
                .sectionId(section.getId())
                .roleId(roleService.getTeacherRoleId())
                .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                .registryOrigin(UserRegistryOriginEnum.SYSTEM.getOrigin())
                .build();

        sectionUserService.save(sectionUser);

        log.info("[SectionAfterCourseAddLinkHandle] add create course user to default section," +
                        " courseId={}, sectionId={}, userId={}",
                courseId, section.getId(), sectionUser.getUserId());

        CourseUser courseUser = CourseUser.builder()
                .userId(WebContext.getUserId())
                .courseId(courseId)
                .isFavorite(Status.YES.getStatus())
                .courseAlias(alias)
                .coverColor(course.getCoverColor())
                .build();
        courseUserDao.save(courseUser);
        log.info("[SectionAfterCourseAddLinkHandle] add create course user to cos_course_user, courseId={}, userId={}",
                courseId, sectionUser.getUserId());

        return linkedInfo;
    }
}

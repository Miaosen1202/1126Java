package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_SECTION
)
public class CourseUserSectionEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/section/edit 用户班级增加\删除
     * @apiDescription 新增用户班级或在班级列表中移除某个用户（新增用户默认为学生角色，移除时：指定移除用户在某个班级中某个角色的信息）
     * @apiName CourseUserSectionEdit
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} userId 用户ID
     * @apiParam {Object[]} sectionUsers 班级用户
     * @apiParam {Number} sectionUsers.sectionId 班级ID
     * @apiParam {Number} [sectionUsers.roleId] 角色ID（班级移除用户时必填）
     *
     *  @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 错误消息/用户ID
     *
     */
    @ValidationParam(clazz = CourseUserSectionChangeVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserSectionChangeVo userSectionChangeVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserSectionChangeVo.class);
        Long courseId = userSectionChangeVo.getCourseId();
        Long userId = userSectionChangeVo.getUserId();

        CourseUser courseUser = courseUserDao.findOne(CourseUser.builder().courseId(courseId).userId(userId).build());
        if (courseUser == null) {
            throw new ParamErrorException();
        }

        Set<Long> sectionIds = userSectionChangeVo.getSectionUsers().stream().map(SectionUser::getSectionId).collect(Collectors.toSet());
        List<Section> sections = sectionDao.findCourseSections(sectionIds, courseId);
        if (ListUtils.isEmpty(sections)) {
            throw new ParamErrorException();
        }

        List<SectionUser> allSectionUsers = sectionUserDao.find(SectionUser.builder().courseId(courseId).userId(userId).build());
        Map<Long, List<SectionUser>> sectionUserSectionIdMap = allSectionUsers.stream()
                .collect(Collectors.groupingBy(SectionUser::getSectionId));

        List<SectionUser> removeSectionUsers = new ArrayList<>();
        for (SectionUser sectionUser : allSectionUsers) {
            boolean isKeep = false;
            if (sectionIds.contains(sectionUser.getSectionId())) {
                for (SectionUser keepSectionUser : userSectionChangeVo.getSectionUsers()) {
                    if (Objects.equals(keepSectionUser.getSectionId(), sectionUser.getSectionId())
                            && Objects.equals(keepSectionUser.getRoleId(), sectionUser.getRoleId())) {
                        isKeep = true;
                        break;
                    }
                }
            }

            if (!isKeep) {
                removeSectionUsers.add(sectionUser);
            }
        }

        for (Section section : sections) {
            if (!sectionUserSectionIdMap.containsKey(section.getId())) {
                Long roleId = roleService.hasTeacherRole() && Objects.equals(userId, WebContext.getUserId())
                        ? roleService.getTeacherRoleId() : roleService.getStudentRoleId();
                SectionUser newSectionUser = SectionUser.builder()
                        .courseId(courseId)
                        .sectionId(section.getId())
                        .userId(userId)
                        .roleId(roleId)
                        .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                        .registryOrigin(UserRegistryOriginEnum.ADMIN.getOrigin())
                        .build();
                sectionUserService.save(newSectionUser);
            }
        }

        removeSectionUser(removeSectionUsers);

        return new LinkedInfo(String.valueOf(userId));
    }

    private void removeSectionUser(List<SectionUser> sectionUsers) {
        for (SectionUser sectionUser : sectionUsers) {
            sectionUserService.delete(sectionUser);
        }
        if (!sectionUsers.isEmpty()) {
            List<Long> courseId = List.of(sectionUsers.get(0).getCourseId());
            studyGroupUserDao.clearNoStudentUser(courseId);
            courseUserDao.deleteUnjoinSectionUsers(courseId);
        }
    }

    @Data
    public static class CourseUserSectionChangeVo {
        @NotNull
        private Long courseId;
        @NotNull
        private Long userId;
        @NotEmpty
        private List<Long> sectionIds;
        @NotEmpty
        private List<SectionUser> sectionUsers;
    }

}

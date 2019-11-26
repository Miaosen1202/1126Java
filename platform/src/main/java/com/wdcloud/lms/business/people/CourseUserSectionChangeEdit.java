package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
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
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_SECTION_CHANGE
)
public class CourseUserSectionChangeEdit implements ISelfDefinedEdit {

    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/sectionChange/edit 课程用户班级编辑
     * @apiName CourseUserSectionChange
     * @apiGroup People
     *
     * @apiParam {Number=0,1} isAdd 0: 移除 1: 添加
     * @apiParam {Number} userId 用户
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} sectionId 班级
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {String} [entity] 用户ID
     *
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserSectionChangeVo userSectionChangeVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserSectionChangeVo.class);

        Long courseId = userSectionChangeVo.getCourseId();
        Long sectionId = userSectionChangeVo.getSectionId();
        Long userId = userSectionChangeVo.getUserId();

        CourseUser courseUser = courseUserDao.findOne(CourseUser.builder()
                .courseId(courseId).userId(userId).build());
        if (courseUser == null) {
            throw new PermissionException();
        }

        Section section = sectionDao.findOne(Section.builder().courseId(courseId).id(sectionId).build());
        if (section == null) {
            throw new PermissionException();
        }

        // todo 用户新增班级角色设置

        if (Objects.equals(Status.YES.getStatus(), userSectionChangeVo.getIsAdd())) {
            SectionUser sectionUser = SectionUser.builder()
                    .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                    .registryOrigin(UserRegistryOriginEnum.ADMIN.getOrigin())
                    .courseId(courseId)
                    .sectionId(sectionId)
                    .userId(userId)
                    .roleId(roleService.getStudentRoleId())
                    .build();
            sectionUserService.save(sectionUser);
        } else {
            sectionUserService.delete(courseId, sectionId, userId, null);
        }

        return new LinkedInfo(String.valueOf(userId));
    }

    @Data
    public static class CourseUserSectionChangeVo {
        @NotNull
        private Long userId;
        @NotNull
        private Long courseId;
        @NotNull
        private Long sectionId;
        @NotNull
        private Integer isAdd;
    }
}

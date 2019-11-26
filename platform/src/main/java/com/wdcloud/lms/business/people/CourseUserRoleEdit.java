package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_ROLE
)
public class CourseUserRoleEdit implements ISelfDefinedEdit {

    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/role/edit 课程用户角色修改
     * @apiName CourseUserRoleEdit
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     * @apiParam {Number} roleId 角色ID
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 用户ID
     *
     */
    @ValidationParam(clazz = CourseUserRoleEditVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserRoleEditVo roleEditVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserRoleEditVo.class);
        if (Objects.equals(WebContext.getUserId(), roleEditVo.getUserId())) {
            throw new PermissionException();
        }

        // 修改用户在课程下所有班级的角色，如果之前在同一班级具有不同角色，需要去掉重复班级用户数据
        Set<Long> userExistsSectionSet = new HashSet<>();
        List<SectionUser> sectionUsers = sectionUserDao.find(SectionUser.builder().courseId(roleEditVo.getCourseId()).userId(roleEditVo.getUserId()).build());
        for (SectionUser sectionUser : sectionUsers) {
            Long sectionId = sectionUser.getSectionId();
            if (!userExistsSectionSet.contains(sectionId)) {
                if (Objects.equals(sectionUser.getRoleId(), roleService.getStudentRoleId())
                        && !Objects.equals(roleEditVo.getRoleId(), sectionUser.getRoleId())) {

                }

                sectionUser.setRoleId(roleEditVo.getRoleId());
                sectionUser.setUpdateUserId(WebContext.getUserId());

                sectionUserDao.update(sectionUser);

                userExistsSectionSet.add(sectionId);
            } else {
                sectionUserService.delete(sectionUser);
            }
        }

        // 如果变更到非学生角色，从学习小组中移除用户关联信息
        if (!Objects.equals(roleService.getStudentRoleId(), roleEditVo.getRoleId()) && ListUtils.isNotEmpty(sectionUsers)) {
            studyGroupUserDao.removeUser(roleEditVo.getCourseId(), roleEditVo.getUserId());
        }

        return new LinkedInfo(String.valueOf(roleEditVo.getUserId()));
    }

    @Data
    public static class CourseUserRoleEditVo {
        @NotNull
        private Long courseId;
        @NotNull
        private Long userId;
        @NotNull
        private Long roleId;
    }
}

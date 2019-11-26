package com.wdcloud.lms.base.service;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.permission.base.dao.RoleDao;
import com.wdcloud.permission.business.role.IUserRoleQuery;
import com.wdcloud.permission.model.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RoleService implements IUserRoleQuery {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Long> getRoleIdsByUserId(String userId) {
        return List.of(WebContext.getRoleId());
    }

    /**
     * 按角色ID查询角色名称
     *
     * @return 返回(角色ID, 角色名称) 对列表 (排除管理员类型角色)
     */
    public Map<Long, String> findRoleIdAndNameMap() {
        List<Role> allRoles = findAllRoles();
        HashMap<Long, String> roleIdAndName = new HashMap<>();
        for (Role role : allRoles) {
            if (!Objects.equals(RoleEnum.ADMIN.getType().intValue(), role.getRoleType())) {
                roleIdAndName.put(role.getId(), role.getRoleName());
            }
        }
        return roleIdAndName;
    }

    /**
     * 查询角色列表
     *
     * @return 返回 roleId, roleName 键值映射列表
     */
    public List<Role> findAllRoles() {
        return roleDao.listAllRoles();
    }


    /**
     * 用户是否在课程中具有教师角色
     *
     * @param courseId
     * @return
     */
    @Deprecated
    public boolean hasTeacherRole(Long courseId) {
        return RoleEnum.TEACHER.getType().equals(WebContext.getRoleId());
    }

    public boolean hasTeacherRole() {
        return RoleEnum.TEACHER.getType().equals(WebContext.getRoleId());
    }

    /**
     * 当前登录用户角色
     *
     * @param courseId courseId
     * @return boolean
     * @see #hasTutorRole()
     */
    @Deprecated
    public boolean hasTutorRole(Long courseId) {
        return RoleEnum.TA.getType().equals(WebContext.getRoleId());
    }

    public boolean hasTutorRole() {
        return RoleEnum.TA.getType().equals(WebContext.getRoleId());
    }

    /**
     * 当前登录用户角色
     *
     * @param courseId courseId
     * @return boolean
     * @see #hasTeacherOrTutorRole()
     */
    @Deprecated
    public boolean hasTeacherOrTutorRole(Long courseId) {
        return hasTeacherRole(courseId) || hasTutorRole(courseId);
    }

    public boolean hasTeacherOrTutorRole() {
        return hasTeacherRole() || hasTutorRole();
    }

    /**
     * 当前登录用户角色
     *
     * @param courseId courseId
     * @return boolean
     * @see #hasStudentRole()
     */
    @Deprecated
    public boolean hasStudentRole(Long courseId) {
        return RoleEnum.STUDENT.getType().equals(WebContext.getRoleId());
    }

    public boolean hasStudentRole() {
        return RoleEnum.STUDENT.getType().equals(WebContext.getRoleId());
    }

    /**
     * 是否管理员角色
     *
     * @return boolean
     */
    public Boolean isAdmin() {
        return roleDao.isAdmin(WebContext.getRoleId());
    }

    /**
     * 获取管理员角色
     *
     * @return boolean
     */
    public Long getAdminRoleId() {
        return roleDao.getAdminId();
    }

    /**
     * 获取学生角色
     *
     * @return boolean
     */
    public Long getStudentRoleId() {
        return RoleEnum.STUDENT.getType();
    }

    /**
     * 获取教师角色
     *
     * @return boolean
     */
    public Long getTeacherRoleId() {
        return RoleEnum.TEACHER.getType();
    }

    /**
     * 获取助教角色
     *
     * @return boolean
     */
    public Long getTaRoleId() {
        return RoleEnum.TA.getType();
    }

}

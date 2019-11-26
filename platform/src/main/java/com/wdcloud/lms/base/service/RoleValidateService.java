package com.wdcloud.lms.base.service;

import com.wdcloud.lms.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleValidateService {

    @Autowired
    private RoleService roleService;

    public void onlyTeacherValid(){
        if(!roleService.hasTeacherRole()){
            throw new PermissionException();
        }
    }

    public void onlyAdminValid(){
        if(!roleService.isAdmin()){
            throw new PermissionException();
        }
    }

    public void teacherAndAdminValid(){
        if(!(roleService.hasTeacherRole() || roleService.isAdmin())){
            throw new PermissionException();
        }
    }
}

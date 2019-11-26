package com.wdcloud.lms.business.course.transfer;

import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.course.enums.CourseStatusToUserEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CourseTransferService {

    @Autowired
    private RoleService roleService;

    public Integer getStatusToUser(Course course, Integer isActive){
        Integer statusToUser = null;

        if(Objects.equals(course.getIsConcluded(), Status.YES.getStatus())){
            statusToUser = CourseStatusToUserEnum.CONCLUDED.getStatus();
        }else if(roleService.hasStudentRole()){
            if(Objects.equals(isActive, Status.NO.getStatus())){
                statusToUser = CourseStatusToUserEnum.FROZEN.getStatus();
            }
        }else{
            statusToUser = CourseStatusToUserEnum.AVAILABLE.getStatus();
        }

        return statusToUser;
    }
}

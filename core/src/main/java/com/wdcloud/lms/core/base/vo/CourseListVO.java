package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CourseListVO extends Course {
    private String orgName;
    private Integer studentCount;
    private List<User> teachers;
}

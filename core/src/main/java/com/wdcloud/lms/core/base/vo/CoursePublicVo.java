package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Course;
import lombok.Data;

@Data
public class CoursePublicVo extends Course {
    private Integer allowOpenRegistry;
    private String coverFileUrl;
    private Long joinedStudentNum;
}

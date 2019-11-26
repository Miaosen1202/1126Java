package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.model.Course;
import lombok.Data;

@Data
public class CourseDashboardVo extends Course {
    private Integer assignmentNumber;
    private Integer discussionNumber;
    private Integer fileNumber;
    private Integer messageNumber;
}
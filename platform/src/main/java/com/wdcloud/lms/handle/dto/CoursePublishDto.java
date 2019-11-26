package com.wdcloud.lms.handle.dto;

import com.wdcloud.lms.core.base.enums.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishDto implements Serializable {
    private Long courseId;
    private Status publishStatus;
}

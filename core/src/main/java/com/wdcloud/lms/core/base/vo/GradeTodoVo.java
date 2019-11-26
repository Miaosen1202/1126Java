package com.wdcloud.lms.core.base.vo;

import lombok.Data;

@Data
public class GradeTodoVo {
    private Long courseId;
    private Integer todoNumber;
    private Integer originType;
}

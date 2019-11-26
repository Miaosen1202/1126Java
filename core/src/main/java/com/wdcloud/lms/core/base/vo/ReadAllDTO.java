package com.wdcloud.lms.core.base.vo;

import lombok.Data;

@Data
public class ReadAllDTO {
    private Long originId;
    private Integer originType;
    private Integer studyGroupId;
    private Long userId;
}

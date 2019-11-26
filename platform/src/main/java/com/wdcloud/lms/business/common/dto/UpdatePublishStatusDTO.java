package com.wdcloud.lms.business.common.dto;

import lombok.Data;

@Data
public class UpdatePublishStatusDTO {
    private Long originId;
    private Integer status;
    private Integer originType;
}

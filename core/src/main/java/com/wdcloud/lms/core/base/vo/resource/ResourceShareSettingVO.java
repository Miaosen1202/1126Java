package com.wdcloud.lms.core.base.vo.resource;

import lombok.Data;

@Data
public class ResourceShareSettingVO {

    private Long id;

    /**
     * 分享范围，2：机构，3：公开
     */
    private Integer shareRange;
}

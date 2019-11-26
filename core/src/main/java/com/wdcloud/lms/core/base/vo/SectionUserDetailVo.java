package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SectionUserDetailVo {
    private Long id;
    private Integer registryStatus;
    private Integer registryOrigin;
    private Date createTime;

    private Long sectionId;
    private String sectionName;

    private Long roleId;
    private String roleName;
}

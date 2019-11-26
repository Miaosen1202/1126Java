package com.wdcloud.lms.core.base.vo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceAdminOperationLogVO {

    private Long id;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源类别
     */
    private Integer category;

    /**
     * 资源分享范围
     */
    private Integer shareRange;

    /**
     * 资源操作类型
     */
    private Integer operationType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 管理员名字
     */
    private String adminName;
}

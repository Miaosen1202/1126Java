package com.wdcloud.lms.core.base.vo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceUpdateLogVO {

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
     * 分享范围
     */
    private Integer shareRange;

    /**
     * 分享类型
     */
     private Integer shareType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer status;

}

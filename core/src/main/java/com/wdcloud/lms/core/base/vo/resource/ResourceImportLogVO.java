package com.wdcloud.lms.core.base.vo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceImportLogVO {

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
     * 课程名称
     */
    private String courseName;

    /**
     * 导入类型
     */
    private String importType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer status;
}

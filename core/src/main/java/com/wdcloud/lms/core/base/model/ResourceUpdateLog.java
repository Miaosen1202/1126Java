package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_update_log")
public class ResourceUpdateLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源名称
     */
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 操作类型，5：第一次分享，4：更新
     */
    @Column(name = "operation_type")
    private Integer operationType;

    /**
     * 分享范围，1：自己，2：机构，3：公开
     */
    @Column(name = "share_range")
    private Integer shareRange;

    /**
     * 类型，1：课程，2：作业，3：测验，4：讨论
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 状态，1：正在进行，2：完成，3：失败
     */
    private Integer status;

    /**
     * 分享人id
     */
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String RESOURCE_NAME = "resourceName";

    public static final String OPERATION_TYPE = "operationType";

    public static final String SHARE_RANGE = "shareRange";

    public static final String ORIGIN_TYPE = "originType";

    public static final String STATUS = "status";

    public static final String USER_ID = "userId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
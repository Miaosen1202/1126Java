package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_admin_operation_log")
public class ResourceAdminOperationLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源的名称
     */
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 分享范围
     */
    @Column(name = "share_range")
    private Integer shareRange;

    /**
     * 分享人ID
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 是否被分享人查看
     */
    @Column(name = "is_see")
    private Integer isSee;

    /**
     * 操作类型，1：编辑，2：移除
     */
    @Column(name = "operation_type")
    private Integer operationType;

    /**
     * 类型，1：课程，2：作业，3：测验，4：讨论
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 资源的名称
     */
    @Column(name = "admin_user_name")
    private String adminUserName;

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

    public static final String SHARE_RANGE = "shareRange";

    public static final String AUTHOR_ID = "authorId";

    public static final String IS_SEE = "isSee";

    public static final String OPERATION_TYPE = "operationType";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ADMIN_USER_NAME = "adminUserName";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
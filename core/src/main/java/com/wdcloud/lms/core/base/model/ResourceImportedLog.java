package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_imported_log")
public class ResourceImportedLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源的名称
     */
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 类型，1：课程，2：作业，3：测验，4：讨论
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 操作类型，3：导入，4：更新
     */
    @Column(name = "operation_type")
    private Integer operationType;

    /**
     * 状态，1：正在进行，2：完成，3：失败
     */
    private Integer status;

    /**
     * 导入人id
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

    public static final String ORIGIN_TYPE = "originType";

    public static final String COURSE_NAME = "courseName";

    public static final String OPERATION_TYPE = "operationType";

    public static final String STATUS = "status";

    public static final String USER_ID = "userId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
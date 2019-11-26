package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

/**
 * 导入资源和生成资源的对应表，作业、讨论、测验是一对一，课程是一对多（一个课程下回导入多个作业、讨论、测验）
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_import_generation")
public class ResourceImportGeneration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 被导入的资源记录id
     */
    @Column(name = "resource_import_id")
    private Long resourceImportId;

    /**
     * 新生成的对象id
     */
    @Column(name = "new_id")
    private Long newId;

    /**
     * 原对象id
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 原对象类型
     */
    @Column(name = "origin_type")
    private Integer originType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String RESOURCE_IMPORT_ID = "resourceImportId";

    public static final String NEW_ID = "newId";

    public static final String ORIGIN_ID = "originId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_org")
public class Org implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sis_id")
    private String sisId;

    /**
     * 上级机构ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 机构树ID
     */
    @Column(name = "tree_id")
    private String treeId;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构类型: 1. 学校, 2. 非学校
     */
    private Integer type;

    /**
     * 机构使用语言（预留）
     */
    private String language;

    /**
     * 机构时区（预留）
     */
    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 机构描述
     */
    private String description;

    public static final String ID = "id";

    public static final String SIS_ID = "sisId";

    public static final String PARENT_ID = "parentId";

    public static final String TREE_ID = "treeId";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String LANGUAGE = "language";

    public static final String TIME_ZONE = "timeZone";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
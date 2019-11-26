package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "p_role")
public class PRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色类型 1超级管理员 2管理员 3业务员 4通用角色（只能超管维护）
     */
    @Column(name = "role_type")
    private Integer roleType;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "tree_id")
    private String treeId;

    private String description;

    /**
     * 状态 1 启用 0 禁用
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String ROLE_NAME = "roleName";

    public static final String ROLE_TYPE = "roleType";

    public static final String PARENT_ID = "parentId";

    public static final String TREE_ID = "treeId";

    public static final String DESCRIPTION = "description";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_TIME = "updateTime";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
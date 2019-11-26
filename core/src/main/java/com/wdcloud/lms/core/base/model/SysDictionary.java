package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_dictionary")
public class SysDictionary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 默认名称
     */
    private String name;

    /**
     * 父字典项ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 树编码，每层编码长度为4
     */
    @Column(name = "tree_id")
    private String treeId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否系统配置字典项，系统配置为基础数据用户不可编辑
     */
    @Column(name = "is_sys_config")
    private Integer isSysConfig;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parentId";

    public static final String TREE_ID = "treeId";

    public static final String STATUS = "status";

    public static final String IS_SYS_CONFIG = "isSysConfig";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
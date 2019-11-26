package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_term")
public class Term implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学期名称
     */
    private String name;

    /**
     * 学期编码
     */
    private String code;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    /**
     * 机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 机构TREE ID
     */
    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * SIS ID
     */
    @Column(name = "sis_id")
    private String sisId;

    /**
     * 是否为默认学期
     */
    @Column(name = "is_default")
    private Integer isDefault;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String ORG_ID = "orgId";

    public static final String ORG_TREE_ID = "orgTreeId";

    public static final String SIS_ID = "sisId";

    public static final String IS_DEFAULT = "isDefault";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
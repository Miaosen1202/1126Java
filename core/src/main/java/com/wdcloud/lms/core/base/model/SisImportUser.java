package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_user")
public class SisImportUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 导致该数据变更的最近导入批次号
     */
    @Column(name = "batch_code")
    private String batchCode;

    /**
     * 导入后系统用户ID
     */
    @Column(name = "target_id")
    private Long targetId;

    /**
     * 所属机构tree_id
     */
    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * SIS ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 登录用户名
     */
    @Column(name = "login_id")
    private String loginId;

    /**
     * 登录密码
     */
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * 用户全称
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 排序名称
     */
    @Column(name = "sortable_name")
    private String sortableName;

    /**
     * 用户简称 nickname
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * SIS 机构ID
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * 邮箱信息
     */
    private String email;

    /**
     * 操作: active, deleted
     */
    private String operation;

    /**
     * 导入任务执行用户
     */
    @Column(name = "op_user_id")
    private Long opUserId;

    /**
     * 执行任务用户所在机构 tree_id
     */
    @Column(name = "op_user_org_tree_id")
    private String opUserOrgTreeId;

    @Column(name = "create_time")
    private Date createTime;

    public static final String ID = "id";

    public static final String BATCH_CODE = "batchCode";

    public static final String TARGET_ID = "targetId";

    public static final String ORG_TREE_ID = "orgTreeId";

    public static final String USER_ID = "userId";

    public static final String LOGIN_ID = "loginId";

    public static final String PASSWORD = "password";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String FULL_NAME = "fullName";

    public static final String SORTABLE_NAME = "sortableName";

    public static final String SHORT_NAME = "shortName";

    public static final String ACCOUNT_ID = "accountId";

    public static final String EMAIL = "email";

    public static final String OPERATION = "operation";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
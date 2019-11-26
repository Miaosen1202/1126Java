package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_org")
public class SisImportOrg implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 导致该数据变更的最近导入批次号
     */
    @Column(name = "batch_code")
    private String batchCode;

    /**
     * 导入后系统机构ID
     */
    @Column(name = "target_id")
    private Long targetId;

    /**
     * 系统机构tree_id
     */
    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * sis 机构ID
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * sis 父机构ID（为空时机构将在导入用户所属机构下）
     */
    @Column(name = "parent_account_id")
    private String parentAccountId;

    private String name;

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

    public static final String ACCOUNT_ID = "accountId";

    public static final String PARENT_ACCOUNT_ID = "parentAccountId";

    public static final String NAME = "name";

    public static final String OPERATION = "operation";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_study_group_user")
public class SisImportStudyGroupUser implements Serializable {
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
     * SIS 小组ID
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * SIS 用户ID
     */
    @Column(name = "user_id")
    private String userId;

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

    public static final String GROUP_ID = "groupId";

    public static final String USER_ID = "userId";

    public static final String OPERATION = "operation";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
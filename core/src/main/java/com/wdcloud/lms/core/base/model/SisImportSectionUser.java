package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_section_user")
public class SisImportSectionUser implements Serializable {
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
     * SIS 课程ID
     */
    @Column(name = "course_id")
    private String courseId;

    /**
     * SIS 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * SIS 班级ID
     */
    @Column(name = "section_id")
    private String sectionId;

    /**
     * SIS 角色: student, ta, teacher, observer
     */
    private String role;

    /**
     * 操作: active, completed, inactive, deleted
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

    public static final String COURSE_ID = "courseId";

    public static final String USER_ID = "userId";

    public static final String SECTION_ID = "sectionId";

    public static final String ROLE = "role";

    public static final String OPERATION = "operation";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
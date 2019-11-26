package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_course")
public class SisImportCourse implements Serializable {
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
     * 课程代码
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 课程名称
     */
    @Column(name = "long_name")
    private String longName;

    /**
     * SIS机构ID
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * SIS学期ID
     */
    @Column(name = "term_id")
    private String termId;

    /**
     * 课程开始时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 课程结束时间
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 课程格式: online, on_campus, blended
     */
    @Column(name = "course_format")
    private String courseFormat;

    /**
     * 操作: active, deleted, completed
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

    public static final String SHORT_NAME = "shortName";

    public static final String LONG_NAME = "longName";

    public static final String ACCOUNT_ID = "accountId";

    public static final String TERM_ID = "termId";

    public static final String START_DATE = "startDate";

    public static final String END_DATE = "endDate";

    public static final String COURSE_FORMAT = "courseFormat";

    public static final String OPERATION = "operation";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
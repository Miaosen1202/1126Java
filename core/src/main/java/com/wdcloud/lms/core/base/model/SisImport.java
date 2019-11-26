package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import")
public class SisImport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_code")
    private String batchCode;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "is_full_batch_update")
    private Integer isFullBatchUpdate;

    @Column(name = "is_override_ui_change")
    private Integer isOverrideUiChange;

    /**
     * ()
     */
    @Column(name = "total_number")
    private Integer totalNumber;

    /**
     * ()
     */
    @Column(name = "org_number")
    private Integer orgNumber;

    /**
     * ()
     */
    @Column(name = "term_number")
    private Integer termNumber;

    /**
     * 成功变更用户数量
     */
    @Column(name = "user_number")
    private Integer userNumber;

    /**
     * ()
     */
    @Column(name = "course_number")
    private Integer courseNumber;

    /**
     * ()
     */
    @Column(name = "section_number")
    private Integer sectionNumber;

    /**
     * ()
     */
    @Column(name = "section_user_number")
    private Integer sectionUserNumber;

    /**
     * ()
     */
    @Column(name = "study_group_set_number")
    private Integer studyGroupSetNumber;

    /**
     * ()
     */
    @Column(name = "study_group_number")
    private Integer studyGroupNumber;

    /**
     * ()
     */
    @Column(name = "study_group_user_number")
    private Integer studyGroupUserNumber;

    @Column(name = "error_number")
    private Integer errorNumber;

    @Column(name = "op_user_id")
    private Long opUserId;

    /**
     *  tree_id
     */
    @Column(name = "op_user_org_tree_id")
    private String opUserOrgTreeId;

    @Column(name = "create_time")
    private Date createTime;

    public static final String ID = "id";

    public static final String BATCH_CODE = "batchCode";

    public static final String ORG_ID = "orgId";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String IS_FULL_BATCH_UPDATE = "isFullBatchUpdate";

    public static final String IS_OVERRIDE_UI_CHANGE = "isOverrideUiChange";

    public static final String TOTAL_NUMBER = "totalNumber";

    public static final String ORG_NUMBER = "orgNumber";

    public static final String TERM_NUMBER = "termNumber";

    public static final String USER_NUMBER = "userNumber";

    public static final String COURSE_NUMBER = "courseNumber";

    public static final String SECTION_NUMBER = "sectionNumber";

    public static final String SECTION_USER_NUMBER = "sectionUserNumber";

    public static final String STUDY_GROUP_SET_NUMBER = "studyGroupSetNumber";

    public static final String STUDY_GROUP_NUMBER = "studyGroupNumber";

    public static final String STUDY_GROUP_USER_NUMBER = "studyGroupUserNumber";

    public static final String ERROR_NUMBER = "errorNumber";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
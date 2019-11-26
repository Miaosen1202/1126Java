package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_course_config")
public class CourseConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 时区（预留字段）
     */
    @Column(name = "time_zone")
    private String timeZone;

    /**
     * 子账号（预留字段）
     */
    @Column(name = "sub_account")
    private String subAccount;

    /**
     * 课程内文件上传限制，单位byte（预留）
     */
    @Column(name = "file_storage_limit")
    private Long fileStorageLimit;

    /**
     * 格式, 1: 校内， 2：在线， 3：混合式
     */
    private Integer format;

    /**
     * 启用课程评分方案
     */
    @Column(name = "enable_grade")
    private Integer enableGrade;

    /**
     * 课程评分方案ID
     */
    @Column(name = "grade_scheme_id")
    private Long gradeSchemeId;

    /**
     * 是否允许开始日期前访问
     */
    @Column(name = "allow_view_before_start_time")
    private Integer allowViewBeforeStartTime;

    /**
     * 是否允许结束日期后访问
     */
    @Column(name = "allow_view_after_end_time")
    private Integer allowViewAfterEndTime;

    /**
     * 开放注册，开放注册后学生可以通过加密链接或Code加入课程（课程为专属时）
     */
    @Column(name = "allow_open_registry")
    private Integer allowOpenRegistry;

    /**
     * 开放注册Code
     */
    @Column(name = "open_registry_code")
    private String openRegistryCode;

    /**
     * 首页显示最新公告
     */
    @Column(name = "enable_homepage_announce")
    private Integer enableHomepageAnnounce;

    /**
     * 首页显示公告数量
     */
    @Column(name = "homepage_announce_number")
    private Integer homepageAnnounceNumber;

    /**
     * 学生可以创建学习小组
     */
    @Column(name = "allow_student_create_study_group")
    private Integer allowStudentCreateStudyGroup;

    /**
     * 在学生评分汇总中隐藏总分
     */
    @Column(name = "hide_total_in_student_grade_summary")
    private Integer hideTotalInStudentGradeSummary;

    /**
     * 对学生隐藏评分分布图
     */
    @Column(name = "hide_grade_distribution_graphs")
    private Integer hideGradeDistributionGraphs;

    /**
     * 允许公告评论
     */
    @Column(name = "allow_announce_comment")
    private Integer allowAnnounceComment;

    /**
     * 课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人
     */
    @Column(name = "course_page_edit_type")
    private Integer coursePageEditType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String TIME_ZONE = "timeZone";

    public static final String SUB_ACCOUNT = "subAccount";

    public static final String FILE_STORAGE_LIMIT = "fileStorageLimit";

    public static final String FORMAT = "format";

    public static final String ENABLE_GRADE = "enableGrade";

    public static final String GRADE_SCHEME_ID = "gradeSchemeId";

    public static final String ALLOW_VIEW_BEFORE_START_TIME = "allowViewBeforeStartTime";

    public static final String ALLOW_VIEW_AFTER_END_TIME = "allowViewAfterEndTime";

    public static final String ALLOW_OPEN_REGISTRY = "allowOpenRegistry";

    public static final String OPEN_REGISTRY_CODE = "openRegistryCode";

    public static final String ENABLE_HOMEPAGE_ANNOUNCE = "enableHomepageAnnounce";

    public static final String HOMEPAGE_ANNOUNCE_NUMBER = "homepageAnnounceNumber";

    public static final String ALLOW_STUDENT_CREATE_STUDY_GROUP = "allowStudentCreateStudyGroup";

    public static final String HIDE_TOTAL_IN_STUDENT_GRADE_SUMMARY = "hideTotalInStudentGradeSummary";

    public static final String HIDE_GRADE_DISTRIBUTION_GRAPHS = "hideGradeDistributionGraphs";

    public static final String ALLOW_ANNOUNCE_COMMENT = "allowAnnounceComment";

    public static final String COURSE_PAGE_EDIT_TYPE = "coursePageEditType";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
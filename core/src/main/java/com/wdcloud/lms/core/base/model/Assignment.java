package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment")
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分
     */
    @Column(name = "grade_type")
    private Integer gradeType;

    /**
     * 评分方案
     */
    @Column(name = "grade_scheme_id")
    private Long gradeSchemeId;

    /**
     * 包含到总成绩统计里
     */
    @Column(name = "is_include_grade")
    private Integer isIncludeGrade;

    /**
     * 提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交
     */
    @Column(name = "submission_type")
    private Integer submissionType;

    /**
     * 外部工具URL，submission_type=3
     */
    @Column(name = "tool_url")
    private String toolUrl;

    /**
     * 内嵌工具（不在新窗口打开工具）
     */
    @Column(name = "is_embed_tool")
    private Integer isEmbedTool;

    /**
     * 在线提交submission_type=1：文本输入
     */
    @Column(name = "allow_text")
    private Integer allowText;

    /**
     * 在线提交submission_type=1：网站地址
     */
    @Column(name = "allow_url")
    private Integer allowUrl;

    /**
     * 在线提交submission_type=1：媒体录音
     */
    @Column(name = "allow_media")
    private Integer allowMedia;

    /**
     * 在线提交submission_type=1：文件上传
     */
    @Column(name = "allow_file")
    private Integer allowFile;

    /**
     * 在线提交submission_type=1&allow_file=1: 上传文件类型限制
     */
    @Column(name = "file_limit")
    private String fileLimit;

    /**
     * 小组作业，小组集ID
     */
    @Column(name = "study_group_set_id")
    private Long studyGroupSetId;

    /**
     * 分别为每位学生指定评分(预留)
     */
    @Column(name = "is_grade_each_one")
    private Integer isGradeEachOne;

    /**
     * 发布状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
     */
    @Column(name = "show_score_type")
    private Integer showScoreType;

    /**
     * 描述
     */
    private String description;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String TITLE = "title";

    public static final String SCORE = "score";

    public static final String GRADE_TYPE = "gradeType";

    public static final String GRADE_SCHEME_ID = "gradeSchemeId";

    public static final String IS_INCLUDE_GRADE = "isIncludeGrade";

    public static final String SUBMISSION_TYPE = "submissionType";

    public static final String TOOL_URL = "toolUrl";

    public static final String IS_EMBED_TOOL = "isEmbedTool";

    public static final String ALLOW_TEXT = "allowText";

    public static final String ALLOW_URL = "allowUrl";

    public static final String ALLOW_MEDIA = "allowMedia";

    public static final String ALLOW_FILE = "allowFile";

    public static final String FILE_LIMIT = "fileLimit";

    public static final String STUDY_GROUP_SET_ID = "studyGroupSetId";

    public static final String IS_GRADE_EACH_ONE = "isGradeEachOne";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String SHOW_SCORE_TYPE = "showScoreType";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
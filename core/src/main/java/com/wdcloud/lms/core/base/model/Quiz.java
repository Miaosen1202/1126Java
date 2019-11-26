package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_quiz")
public class Quiz implements Serializable {
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
     * 测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)
     */
    private Integer type;

    /**
     * 作业小组ID（type=2,3）
     */
    @Column(name = "assignment_group_id")
    private Long assignmentGroupId;

    /**
     * 计分值(type=3)
     */
    private Integer score;

    /**
     * 允许匿名提交(type=3,4)
     */
    @Column(name = "allow_anonymous")
    private Integer allowAnonymous;

    /**
     * 是否重组答案
     */
    @Column(name = "is_shuffle_answer")
    private Integer isShuffleAnswer;

    /**
     * 时间限制，单位分钟
     */
    @Column(name = "time_limit")
    private Integer timeLimit;

    /**
     * 允许多次尝试
     */
    @Column(name = "allow_multi_attempt")
    private Integer allowMultiAttempt;

    /**
     * 尝试次数
     */
    @Column(name = "attempt_number")
    private Integer attemptNumber;

    /**
     * 多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分
     */
    @Column(name = "score_type")
    private Integer scoreType;

    /**
     * 显示学生的回答是否正确策略，0: 不显示 1：每次提交答案后 2：最后一次提交后
     */
    @Column(name = "show_reply_strategy")
    private Integer showReplyStrategy;

    /**
     * 显示正确答案策略，0: 不显示 1：每次提交后 2：最后一次提交后
     */
    @Column(name = "show_answer_strategy")
    private Integer showAnswerStrategy;

    /**
     * 显示正确答案开始时间
     */
    @Column(name = "show_answer_start_time")
    private Date showAnswerStartTime;

    /**
     * 显示正确答案结束时间
     */
    @Column(name = "show_answer_end_time")
    private Date showAnswerEndTime;

    /**
     * 显示问题策略, 0: 全部显示, 1: 每页一个
     */
    @Column(name = "show_question_strategy")
    private Integer showQuestionStrategy;

    /**
     * 回答后锁定问题
     */
    @Column(name = "is_lock_replied_question")
    private Integer isLockRepliedQuestion;

    /**
     * 是否需要访问码访问
     */
    @Column(name = "is_need_access_code")
    private Integer isNeedAccessCode;

    /**
     * 访问码
     */
    @Column(name = "access_code")
    private String accessCode;

    /**
     * 是否过滤访问IP
     */
    @Column(name = "is_filter_ip")
    private Integer isFilterIp;

    /**
     * 过滤IP地址
     */
    @Column(name = "filter_ip_address")
    private String filterIpAddress;

    /**
     * 版本号（预留）,每次更新后版本号增加
     */
    private Integer version;

    /**
     * 发布状态
     */
    private Integer status;

    /**
     * 问题总数
     */
    @Column(name = "total_questions")
    private Integer totalQuestions;

    /**
     * 总分
     */
    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 描述
     */
    private String description;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String TITLE = "title";

    public static final String TYPE = "type";

    public static final String ASSIGNMENT_GROUP_ID = "assignmentGroupId";

    public static final String SCORE = "score";

    public static final String ALLOW_ANONYMOUS = "allowAnonymous";

    public static final String IS_SHUFFLE_ANSWER = "isShuffleAnswer";

    public static final String TIME_LIMIT = "timeLimit";

    public static final String ALLOW_MULTI_ATTEMPT = "allowMultiAttempt";

    public static final String ATTEMPT_NUMBER = "attemptNumber";

    public static final String SCORE_TYPE = "scoreType";

    public static final String SHOW_REPLY_STRATEGY = "showReplyStrategy";

    public static final String SHOW_ANSWER_STRATEGY = "showAnswerStrategy";

    public static final String SHOW_ANSWER_START_TIME = "showAnswerStartTime";

    public static final String SHOW_ANSWER_END_TIME = "showAnswerEndTime";

    public static final String SHOW_QUESTION_STRATEGY = "showQuestionStrategy";

    public static final String IS_LOCK_REPLIED_QUESTION = "isLockRepliedQuestion";

    public static final String IS_NEED_ACCESS_CODE = "isNeedAccessCode";

    public static final String ACCESS_CODE = "accessCode";

    public static final String IS_FILTER_IP = "isFilterIp";

    public static final String FILTER_IP_ADDRESS = "filterIpAddress";

    public static final String VERSION = "version";

    public static final String STATUS = "status";

    public static final String TOTAL_QUESTIONS = "totalQuestions";

    public static final String TOTAL_SCORE = "totalScore";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
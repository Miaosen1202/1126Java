package com.wdcloud.lms.core.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_grade")
public class Grade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    /**
     * 作业组item
     */
    @Column(name = "assignment_group_item_id")
    private Long assignmentGroupItemId;

    /**
     * 任务类型： 1: 作业 2: 讨论 3: 测验
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 来源ID
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分
     */
    @Column(name = "score_type")
    private Integer scoreType;

    /**
     * 总分
     */
    private Integer score;

    /**
     * 得分
     */
    @Column(name = "grade_score")
    private Integer gradeScore;

    /**
     * 最后一次得分
     */
    @Column(name = "current_score")
    private Integer currentScore;

    /**
     * 是否已全部评分，0：否；1：是
     */
    @Column(name = "is_graded")
    private Integer isGraded;

    /**
     * 最后一次完成该任务所用时间
     */
    @Column(name = "time_limit")
    private Integer timeLimit;

    /**
     * 记录最终分数的记录id，只有score_type=1、2时需要
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 已尝试次数
     */
    private Integer attemps;

    /**
     * 剩余尝试次数
     */
    @Column(name = "attemps_available")
    private Integer attempsAvailable;

    /**
     * 是否为评分任务0：否；1：是；
     */
    @Column(name = "need_grade")
    private Integer needGrade;

    /**
     * 是否逾期提交,是否逾期只以第一次提交时时间判断;0:否；1：是；
     */
    @Column(name = "is_overdue")
    private Integer isOverdue;

    /**
     * 最近提交时间
     */
    @Column(name = "last_submit_time")
    private Date lastSubmitTime;

    /**
     * 提交次数
     */
    @Column(name = "submit_count")
    private Integer submitCount;

    /**
     * 提交用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 评分用户
     */
    @Column(name = "grader_id")
    private Long graderId;

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

    public static final String ASSIGNMENT_GROUP_ITEM_ID = "assignmentGroupItemId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String SCORE_TYPE = "scoreType";

    public static final String SCORE = "score";

    public static final String GRADE_SCORE = "gradeScore";

    public static final String CURRENT_SCORE = "currentScore";

    public static final String IS_GRADED = "isGraded";

    public static final String TIME_LIMIT = "timeLimit";

    public static final String RECORD_ID = "recordId";

    public static final String ATTEMPS = "attemps";

    public static final String ATTEMPS_AVAILABLE = "attempsAvailable";

    public static final String NEED_GRADE = "needGrade";

    public static final String IS_OVERDUE = "isOverdue";

    public static final String LAST_SUBMIT_TIME = "lastSubmitTime";

    public static final String SUBMIT_COUNT = "submitCount";

    public static final String USER_ID = "userId";

    public static final String GRADER_ID = "graderId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
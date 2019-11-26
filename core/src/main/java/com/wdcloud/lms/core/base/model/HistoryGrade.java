package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_history_grade")
public class HistoryGrade implements Serializable {
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
     * 总分
     */
    private Integer score;

    /**
     * 得分
     */
    @Column(name = "grade_score")
    private Integer gradeScore;

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

    public static final String SCORE = "score";

    public static final String GRADE_SCORE = "gradeScore";

    public static final String USER_ID = "userId";

    public static final String GRADER_ID = "graderId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
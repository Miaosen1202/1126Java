package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_user_submit_record")
public class UserSubmitRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 提交用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 学习小组
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 关联作业组项
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
     * 最近提交
     */
    @Column(name = "last_submit_time")
    private Date lastSubmitTime;

    /**
     * 提交次数
     */
    @Column(name = "submit_count")
    private Integer submitCount;

    /**
     * 是否已评分
     */
    @Column(name = "is_graded")
    private Integer isGraded;

    /**
     * 得分
     */
    @Column(name = "grade_score")
    private Integer gradeScore;

    /**
     * 是否为评分任务
     */
    @Column(name = "need_grade")
    private Integer needGrade;

    /**
     * 是否逾期提交,是否逾期只以第一次提交时时间判断
     */
    @Column(name = "is_overdue")
    private Integer isOverdue;

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

    public static final String USER_ID = "userId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String ASSIGNMENT_GROUP_ITEM_ID = "assignmentGroupItemId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String LAST_SUBMIT_TIME = "lastSubmitTime";

    public static final String SUBMIT_COUNT = "submitCount";

    public static final String IS_GRADED = "isGraded";

    public static final String GRADE_SCORE = "gradeScore";

    public static final String NEED_GRADE = "needGrade";

    public static final String IS_OVERDUE = "isOverdue";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
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
@Table(name = "cos_quiz_record")
public class QuizRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 测验ID
     */
    @Column(name = "quiz_id")
    private Long quizId;

    /**
     * 提交时测验版本
     */
    @Column(name = "quiz_version")
    private Integer quizVersion;

    /**
     * 是否确认提交
     */
    @Column(name = "is_submit")
    private Integer isSubmit;

    /**
     * 是否最后一次提交（最后一次提交后不再允许进行提交）
     */
    @Column(name = "is_last_time")
    private Integer isLastTime;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 提交时间
     */
    @Column(name = "submit_time")
    private Date submitTime;

    /**
     * 提交截至时间，开始测验时根据测验的时间限制计算
     */
    @Column(name = "due_time")
    private Date dueTime;

    /**
     * 测验人ID
     */
    @Column(name = "tester_id")
    private Long testerId;

    /**
     * 测试人角色类型
     */
    @Column(name = "tester_role_type")
    private Integer testerRoleType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String QUIZ_ID = "quizId";

    public static final String QUIZ_VERSION = "quizVersion";

    public static final String IS_SUBMIT = "isSubmit";

    public static final String IS_LAST_TIME = "isLastTime";

    public static final String START_TIME = "startTime";

    public static final String SUBMIT_TIME = "submitTime";

    public static final String DUE_TIME = "dueTime";

    public static final String TESTER_ID = "testerId";

    public static final String TESTER_ROLE_TYPE= "testerRoleType";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
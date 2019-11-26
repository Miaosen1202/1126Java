package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assign")
public class Assign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 来源类型，1: 作业 2:讨论 3:测验  4: 文件 5：页面 6：公告
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 来源ID
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 分配类型，1: 所有， 2：section(班级), 3: 用户
     */
    @Column(name = "assign_type")
    private Integer assignType;

    /**
     * 分配目标ID
     */
    @Column(name = "assign_id")
    private Long assignId;

    /**
     * 规定截止日期
     */
    @Column(name = "limit_time")
    private Date limitTime;

    /**
     * 可以参加测验开始日期
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 可以参加测验结束日期
     */
    @Column(name = "end_time")
    private Date endTime;

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

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String ASSIGN_TYPE = "assignType";

    public static final String ASSIGN_ID = "assignId";

    public static final String LIMIT_TIME = "limitTime";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
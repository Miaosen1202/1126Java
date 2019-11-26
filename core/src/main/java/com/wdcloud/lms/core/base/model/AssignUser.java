package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assign_user")
public class AssignUser implements Serializable {
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
     * 被分配到的用户ID
     */
    @Column(name = "user_id")
    private Long userId;

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

    private static final long serialVersionUID = 1L;
}
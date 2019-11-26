package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment_group_item")
public class AssignmentGroupItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组ID
     */
    @Column(name = "assignment_group_id")
    private Long assignmentGroupId;

    /**
     * 目标任务类型标题(更新由触发器维护)
     */
    private String title;

    /**
     * 目标任务类型分值(更新由触发器维护)
     */
    private Integer score;

    /**
     * 目标任务类型发布状态(更新由触发器维护)
     */
    private Integer status;

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
     * 排序
     */
    private Integer seq;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String ASSIGNMENT_GROUP_ID = "assignmentGroupId";

    public static final String TITLE = "title";

    public static final String SCORE = "score";

    public static final String STATUS = "status";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
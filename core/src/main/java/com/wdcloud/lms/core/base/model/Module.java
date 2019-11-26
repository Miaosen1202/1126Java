package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_module")
public class Module implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 名称
     */
    private String name;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 单元要求完成策略，1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个
     */
    @Column(name = "complete_strategy")
    private Integer completeStrategy;

    /**
     * 发布状态
     */
    private Integer status;

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

    public static final String COURSE_ID = "courseId";

    public static final String NAME = "name";

    public static final String START_TIME = "startTime";

    public static final String COMPLETE_STRATEGY = "completeStrategy";

    public static final String STATUS = "status";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
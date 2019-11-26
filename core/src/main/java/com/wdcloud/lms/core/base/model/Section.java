package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_section")
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sis_id")
    private String sisId;

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
     * 是否默认课程
     */
    @Column(name = "is_default")
    private Integer isDefault;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 用户只能在时间范围内参与课程练习（预留）
     */
    @Column(name = "is_limit")
    private Integer isLimit;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String SIS_ID = "sisId";

    public static final String COURSE_ID = "courseId";

    public static final String NAME = "name";

    public static final String IS_DEFAULT = "isDefault";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String IS_LIMIT = "isLimit";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
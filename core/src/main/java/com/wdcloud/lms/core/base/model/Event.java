package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日历类型, 1: 个人 2: 课程 3: 学习小组
     */
    @Column(name = "calendar_type")
    private Integer calendarType;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 所属课程(calendar=2,3时非空)
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 所属小组(calendar=3时非空)
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 标题
     */
    private String title;

    /**
     * 地址
     */
    private String address;

    /**
     * 位置
     */
    private String location;

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

    public static final String CALENDAR_TYPE = "calendarType";

    public static final String USER_ID = "userId";

    public static final String COURSE_ID = "courseId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String TITLE = "title";

    public static final String ADDRESS = "address";

    public static final String LOCATION = "location";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
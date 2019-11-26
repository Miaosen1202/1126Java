package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_user_todo")
public class UserTodo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日历类型, 1: 个人 2: 课程
     */
    @Column(name = "calendar_type")
    private Integer calendarType;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 所属课程(calendar_type=2时非空)
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 执行时间
     */
    @Column(name = "do_time")
    private Date doTime;

    /**
     * 标题
     */
    private String title;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 内容
     */
    private String content;

    public static final String ID = "id";

    public static final String CALENDAR_TYPE = "calendarType";

    public static final String USER_ID = "userId";

    public static final String COURSE_ID = "courseId";

    public static final String DO_TIME = "doTime";

    public static final String TITLE = "title";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
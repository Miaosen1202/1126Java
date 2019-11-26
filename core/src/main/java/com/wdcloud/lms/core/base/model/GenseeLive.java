package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "gensee_live")
public class GenseeLive implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 主讲人
     */
    private Long instructor;

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
     * 直播ID
     */
    @Column(name = "gensee_live_id")
    private String genseeLiveId;

    /**
     * 组织者加入URL
     */
    @Column(name = "gensee_organizer_join_url")
    private String genseeOrganizerJoinUrl;

    /**
     * 嘉宾加入URL
     */
    @Column(name = "gensee_panelist_join_url")
    private String genseePanelistJoinUrl;

    /**
     * 普通参加者加入URL(带token)
     */
    @Column(name = "gensee_attendee_join_url")
    private String genseeAttendeeJoinUrl;

    /**
     * 直播编号
     */
    @Column(name = "gensee_number")
    private String genseeNumber;

    /**
     * 普通参加者加入URL(不带token)
     */
    @Column(name = "gensee_attendee_a_short_join_url")
    private String genseeAttendeeAShortJoinUrl;

    /**
     * 组织者口令
     */
    @Column(name = "gensee_organizer_token")
    private String genseeOrganizerToken;

    /**
     * 嘉宾口令
     */
    @Column(name = "gensee_panelist_token")
    private String genseePanelistToken;

    /**
     * 普通参加者口令
     */
    @Column(name = "gensee_attendee_token")
    private String genseeAttendeeToken;

    /**
     * 是否删除 0：否；1：是
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;

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

    public static final String COURSE_ID = "courseId";

    public static final String TITLE = "title";

    public static final String INSTRUCTOR = "instructor";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String GENSEE_LIVE_ID = "genseeLiveId";

    public static final String GENSEE_ORGANIZER_JOIN_URL = "genseeOrganizerJoinUrl";

    public static final String GENSEE_PANELIST_JOIN_URL = "genseePanelistJoinUrl";

    public static final String GENSEE_ATTENDEE_JOIN_URL = "genseeAttendeeJoinUrl";

    public static final String GENSEE_NUMBER = "genseeNumber";

    public static final String GENSEE_ATTENDEE_A_SHORT_JOIN_URL = "genseeAttendeeAShortJoinUrl";

    public static final String GENSEE_ORGANIZER_TOKEN = "genseeOrganizerToken";

    public static final String GENSEE_PANELIST_TOKEN = "genseePanelistToken";

    public static final String GENSEE_ATTENDEE_TOKEN = "genseeAttendeeToken";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
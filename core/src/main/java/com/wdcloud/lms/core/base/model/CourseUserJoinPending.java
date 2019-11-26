package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_course_user_join_pending")
public class CourseUserJoinPending implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "section_user_id")
    private Long sectionUserId;

    /**
     * 课程发布状态,已发布后,用户登录系统,如果存在未处理的加入课程消息,会提示用户
     */
    @Column(name = "public_status")
    private Integer publicStatus;

    /**
     * 邀请码
     */
    private String code;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String COURSE_ID = "courseId";

    public static final String SECTION_ID = "sectionId";

    public static final String ROLE_ID = "roleId";

    public static final String SECTION_USER_ID = "sectionUserId";

    public static final String PUBLIC_STATUS = "publicStatus";

    public static final String CODE = "code";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_study_group_user")
public class StudyGroupUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 组集ID
     */
    @Column(name = "study_group_set_id")
    private Long studyGroupSetId;

    /**
     * 组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否为组长
     */
    @Column(name = "is_leader")
    private Integer isLeader;

    /**
     * 前景色，格式: #666666(日历使用)
     */
    @Column(name = "cover_color")
    private String coverColor;

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

    public static final String STUDY_GROUP_SET_ID = "studyGroupSetId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String USER_ID = "userId";

    public static final String IS_LEADER = "isLeader";

    public static final String COVER_COLOR = "coverColor";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
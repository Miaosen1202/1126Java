package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_course_user")
public class CourseUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 收藏课程：收藏的课程将在Dashboard中显示
     */
    @Column(name = "is_favorite")
    private Integer isFavorite;

    /**
     * 课程前景色，格式: #666666
     */
    @Column(name = "cover_color")
    private String coverColor;

    /**
     * 课程别名
     */
    @Column(name = "course_alias")
    private String courseAlias;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 是否激活
     */
    @Column(name = "is_active")
    private Integer isActive;

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

    public static final String USER_ID = "userId";

    public static final String IS_FAVORITE = "isFavorite";

    public static final String COVER_COLOR = "coverColor";

    public static final String COURSE_ALIAS = "courseAlias";

    public static final String SEQ = "seq";

    public static final String IS_ACTIVE = "isActive";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
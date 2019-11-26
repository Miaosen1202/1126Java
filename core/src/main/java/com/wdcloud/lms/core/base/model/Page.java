package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_page")
public class Page implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 页面查看地址
     */
    private String url;

    /**
     * 编辑类型：1. 教师 2. 教师与学生 3. 任何人
     */
    @Column(name = "editor_type")
    private Integer editorType;

    /**
     * 小组Page，小组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * todo时间
     */
    @Column(name = "todo_time")
    private Date todoTime;

    /**
     * 是否为FrontPage
     */
    @Column(name = "is_front_page")
    private Integer isFrontPage;

    /**
     * 发布状态
     */
    private Integer status;

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

    public static final String COURSE_ID = "courseId";

    public static final String TITLE = "title";

    public static final String URL = "url";

    public static final String EDITOR_TYPE = "editorType";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String TODO_TIME = "todoTime";

    public static final String IS_FRONT_PAGE = "isFrontPage";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
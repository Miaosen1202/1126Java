package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_discussion_config")
public class DiscussionConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 允许学生讨论上传附件
     */
    @Column(name = "allow_discussion_attach_file")
    private Integer allowDiscussionAttachFile;

    /**
     * 学生可创建讨论
     */
    @Column(name = "allow_student_create_discussion")
    private Integer allowStudentCreateDiscussion;

    /**
     * 学生可编辑/删除自己话题和评论
     */
    @Column(name = "allow_student_edit_discussion")
    private Integer allowStudentEditDiscussion;

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

    public static final String ALLOW_DISCUSSION_ATTACH_FILE = "allowDiscussionAttachFile";

    public static final String ALLOW_STUDENT_CREATE_DISCUSSION = "allowStudentCreateDiscussion";

    public static final String ALLOW_STUDENT_EDIT_DISCUSSION = "allowStudentEditDiscussion";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
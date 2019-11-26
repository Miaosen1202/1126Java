package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment_reply")
public class AssignmentReply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    /**
     * 作业ID
     */
    @Column(name = "assignment_id")
    private Long assignmentId;

    /**
     * 提交用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 小组作业时，组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 提交时间
     */
    @Column(name = "submit_time")
    private Date submitTime;

    /**
     * 1: 文本　2: 文件 3: URL 4: 媒体
     */
    @Column(name = "reply_type")
    private Integer replyType;

    /**
     * 是否删除
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
     * 回答内容
     */
    private String content;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String ASSIGNMENT_ID = "assignmentId";

    public static final String USER_ID = "userId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String SUBMIT_TIME = "submitTime";

    public static final String REPLY_TYPE = "replyType";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
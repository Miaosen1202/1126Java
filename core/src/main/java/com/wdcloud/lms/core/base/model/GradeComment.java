package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_grade_comment")
public class GradeComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    /**
     * 作业组item
     */
    @Column(name = "assignment_group_item_id")
    private Long assignmentGroupItemId;

    /**
     * 任务类型： 1: 作业 2: 讨论 3: 测验
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 来源ID
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 评论用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 所属学习小组
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 附件ID
     */
    @Column(name = "attachment_file_id")
    private Long attachmentFileId;

    /**
     * 小组作业时，评论是否组成员可见(预留)
     */
    @Column(name = "is_send_group_user")
    private Integer isSendGroupUser;

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
     * 评论内容
     */
    private String content;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String ASSIGNMENT_GROUP_ITEM_ID = "assignmentGroupItemId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String USER_ID = "userId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String ATTACHMENT_FILE_ID = "attachmentFileId";

    public static final String IS_SEND_GROUP_USER = "isSendGroupUser";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
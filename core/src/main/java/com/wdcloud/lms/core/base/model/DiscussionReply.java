package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_discussion_reply")
public class DiscussionReply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 讨论ID
     */
    @Column(name = "discussion_id")
    private Long discussionId;

    /**
     * 学习小组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 回复的回复ID(这个ID表示这是一个回复的回复)
     */
    @Column(name = "discussion_reply_id")
    private Long discussionReplyId;

    /**
     * 树ID
     */
    @Column(name = "tree_id")
    private String treeId;

    /**
     * 删除标志
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;

    /**
     * 附件文件ID
     */
    @Column(name = "attachment_file_id")
    private Long attachmentFileId;

    @Column(name = "role_id")
    private Long roleId;

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

    public static final String DISCUSSION_ID = "discussionId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String DISCUSSION_REPLY_ID = "discussionReplyId";

    public static final String TREE_ID = "treeId";

    public static final String IS_DELETED = "isDeleted";

    public static final String ATTACHMENT_FILE_ID = "attachmentFileId";

    public static final String ROLE_ID = "roleId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
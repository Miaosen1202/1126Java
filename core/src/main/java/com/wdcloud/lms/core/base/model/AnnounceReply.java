package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_announce_reply")
public class AnnounceReply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公告ID
     */
    @Column(name = "announce_id")
    private Long announceId;

    /**
     * 学习小组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 浏览数量
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 删除状态
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;

    /**
     * 回复的ID
     */
    @Column(name = "announce_reply_id")
    private Long announceReplyId;

    /**
     * 附件关联文件ID
     */
    @Column(name = "attachment_file_id")
    private Long attachmentFileId;

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

    public static final String ANNOUNCE_ID = "announceId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String VIEW_COUNT = "viewCount";

    public static final String IS_DELETED = "isDeleted";

    public static final String ANNOUNCE_REPLY_ID = "announceReplyId";

    public static final String ATTACHMENT_FILE_ID = "attachmentFileId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
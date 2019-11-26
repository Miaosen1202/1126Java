package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_announce")
public class Announce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 小组公告，小组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 主题
     */
    private String topic;

    /**
     * 指定日期才发布
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 允许评论
     */
    @Column(name = "allow_comment")
    private Integer allowComment;

    /**
     * 限制回复后才能查看评论
     */
    @Column(name = "is_comment_before_see_reply")
    private Integer isCommentBeforeSeeReply;

    /**
     * 点赞策略，0：不允许 1：允许 2：只有评分者才能点赞
     */
    @Column(name = "like_strategy")
    private Integer likeStrategy;

    /**
     * 按赞数排序
     */
    @Column(name = "is_order_by_like")
    private Integer isOrderByLike;

    /**
     * 浏览数量
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 附件关联文件ID
     */
    @Column(name = "attachment_file_id")
    private Long attachmentFileId;

    /**
     * 删除状态
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
     * 内容
     */
    private String content;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String TOPIC = "topic";

    public static final String PUBLISH_TIME = "publishTime";

    public static final String ALLOW_COMMENT = "allowComment";

    public static final String IS_COMMENT_BEFORE_SEE_REPLY = "isCommentBeforeSeeReply";

    public static final String LIKE_STRATEGY = "likeStrategy";

    public static final String IS_ORDER_BY_LIKE = "isOrderByLike";

    public static final String VIEW_COUNT = "viewCount";

    public static final String ATTACHMENT_FILE_ID = "attachmentFileId";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
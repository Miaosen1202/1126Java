package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_discussion")
public class Discussion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 1: 普通讨论， 2： 小组讨论
     */
    private Integer type;

    /**
     * 学习小组集ID
     */
    @Column(name = "study_group_set_id")
    private Long studyGroupSetId;

    /**
     * 学习小组ID
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 是否置顶
     */
    @Column(name = "is_pin")
    private Integer isPin;

    /**
     * 置顶顺序
     */
    @Column(name = "pin_seq")
    private Integer pinSeq;

    /**
     * 发布状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 是否评分
     */
    @Column(name = "is_grade")
    private Integer isGrade;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分
     */
    @Column(name = "grade_type")
    private Integer gradeType;

    /**
     * 评分方案
     */
    @Column(name = "grade_scheme_id")
    private Long gradeSchemeId;

    /**
     * 允许点赞
     */
    @Column(name = "allow_like")
    private Integer allowLike;

    /**
     * 允许评论
     */
    @Column(name = "allow_comment")
    private Integer allowComment;

    /**
     * 是否必须在评论后才能查看其他回复
     */
    @Column(name = "is_comment_before_see_reply")
    private Integer isCommentBeforeSeeReply;

    /**
     * 附件文件ID
     */
    @Column(name = "attachment_file_id")
    private Long attachmentFileId;

    /**
     * 最近活动时间
     */
    @Column(name = "last_active_time")
    private Date lastActiveTime;

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
     * 内容
     */
    private String content;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String TITLE = "title";

    public static final String TYPE = "type";

    public static final String STUDY_GROUP_SET_ID = "studyGroupSetId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String IS_PIN = "isPin";

    public static final String PIN_SEQ = "pinSeq";

    public static final String STATUS = "status";

    public static final String SEQ = "seq";

    public static final String IS_GRADE = "isGrade";

    public static final String SCORE = "score";

    public static final String GRADE_TYPE = "gradeType";

    public static final String GRADE_SCHEME_ID = "gradeSchemeId";

    public static final String ALLOW_LIKE = "allowLike";

    public static final String ALLOW_COMMENT = "allowComment";

    public static final String IS_COMMENT_BEFORE_SEE_REPLY = "isCommentBeforeSeeReply";

    public static final String ATTACHMENT_FILE_ID = "attachmentFileId";

    public static final String LAST_ACTIVE_TIME = "lastActiveTime";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
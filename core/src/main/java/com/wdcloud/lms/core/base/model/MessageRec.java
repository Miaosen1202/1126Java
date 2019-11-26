package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_message_rec")
public class MessageRec implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发送者Id
     */
    @Column(name = "send_id")
    private Long sendId;

    /**
     * 接收者ID
     */
    @Column(name = "rec_id")
    private Long recId;

    /**
     * 主题ID
     */
    @Column(name = "subject_id")
    private Long subjectId;

    /**
     * 站内信内容ID
     */
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 是否收藏
     */
    @Column(name = "is_star")
    private Integer isStar;

    /**
     * 是否系统默认
     */
    @Column(name = "is_default")
    private Integer isDefault;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String SEND_ID = "sendId";

    public static final String REC_ID = "recId";

    public static final String SUBJECT_ID = "subjectId";

    public static final String MESSAGE_ID = "messageId";

    public static final String IS_READ = "isRead";

    public static final String IS_STAR = "isStar";

    public static final String IS_DEFAULT = "isDefault";

    public static final String IS_DELETE = "isDelete";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
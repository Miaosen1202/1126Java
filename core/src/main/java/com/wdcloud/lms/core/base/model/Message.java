package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_subject")
    private Integer isSubject;

    @Column(name = "subject_id")
    private Long subjectId;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "is_private")
    private Integer isPrivate;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 消息主题
     */
    @Column(name = "message_subject")
    private String messageSubject;

    /**
     * 消息内容
     */
    @Column(name = "message_text")
    private String messageText;

    public static final String ID = "id";

    public static final String IS_SUBJECT = "isSubject";

    public static final String SUBJECT_ID = "subjectId";

    public static final String COURSE_ID = "courseId";

    public static final String IS_PRIVATE = "isPrivate";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String MESSAGE_SUBJECT = "messageSubject";

    public static final String MESSAGE_TEXT = "messageText";

    private static final long serialVersionUID = 1L;
}
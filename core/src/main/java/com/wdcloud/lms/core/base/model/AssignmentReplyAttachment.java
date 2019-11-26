package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment_reply_attachment")
public class AssignmentReplyAttachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 作业ID
     */
    @Column(name = "assignment_id")
    private Long assignmentId;

    /**
     * 作业回答ID
     */
    @Column(name = "assignment_reply_id")
    private Long assignmentReplyId;

    /**
     * 提交文件ID
     */
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String ASSIGNMENT_ID = "assignmentId";

    public static final String ASSIGNMENT_REPLY_ID = "assignmentReplyId";

    public static final String FILE_ID = "fileId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
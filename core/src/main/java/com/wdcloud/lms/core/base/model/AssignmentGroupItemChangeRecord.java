package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment_group_item_change_record")
public class AssignmentGroupItemChangeRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    /**
     * 任务项ID
     */
    @Column(name = "assignment_group_item_id")
    private Long assignmentGroupItemId;

    /**
     * 任务项标题
     */
    private String title;

    @Column(name = "origin_id")
    private Long originId;

    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 操作类型， 1： 创建 2: 更新内容 3: 更新StartTime, 4: 更新EndTime 5: 更新DueTime
     */
    @Column(name = "op_type")
    private Integer opType;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 任务项内容
     */
    private String content;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String ASSIGNMENT_GROUP_ITEM_ID = "assignmentGroupItemId";

    public static final String TITLE = "title";

    public static final String ORIGIN_ID = "originId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String OP_TYPE = "opType";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String CREATE_TIME = "createTime";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}
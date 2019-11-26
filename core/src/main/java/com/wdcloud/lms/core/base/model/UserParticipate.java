package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_user_participate")
public class UserParticipate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参与对象关联课程
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 活动用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 活动对象类别: 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 活动对象ID
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 活动类型： 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）
     */
    private Integer operation;

    /**
     * 活动对象名称
     */
    @Column(name = "target_name")
    private String targetName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String USER_ID = "userId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String ORIGIN_ID = "originId";

    public static final String OPERATION = "operation";

    public static final String TARGET_NAME = "targetName";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    private static final long serialVersionUID = 1L;
}
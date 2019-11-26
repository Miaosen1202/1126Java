package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_module_progression_item")
public class ModuleProgressionItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 课程
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 课程单元
     */
    @Column(name = "module_id")
    private Long moduleId;

    /**
     * 单元项ID
     */
    @Column(name = "module_item_id")
    private Long moduleItemId;

    /**
     * 是否完成
     */
    @Column(name = "is_finished")
    private Integer isFinished;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String COURSE_ID = "courseId";

    public static final String MODULE_ID = "moduleId";

    public static final String MODULE_ITEM_ID = "moduleItemId";

    public static final String IS_FINISHED = "isFinished";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
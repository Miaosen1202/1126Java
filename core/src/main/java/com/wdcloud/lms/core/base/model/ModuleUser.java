package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_module_user")
public class ModuleUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 单元ID
     */
    @Column(name = "module_id")
    private Long moduleId;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 被分配到的用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 完成状态，0：未完成；1：完成；2：过期（由前端生成数据时处理，后端不做处理，依据状态和过期时间判断）；
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String MODULE_ID = "moduleId";

    public static final String COURSE_ID = "courseId";

    public static final String USER_ID = "userId";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
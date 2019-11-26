package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_section_user")
public class SectionUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    /**
     * 注册状态, 1: 邀请中, 2: 加入
     */
    @Column(name = "registry_status")
    private Integer registryStatus;

    /**
     * 注册来源, 1: 管理员添加，2：邀请加入，3：自行加入
     */
    @Column(name = "registry_origin")
    private Integer registryOrigin;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String SECTION_ID = "sectionId";

    public static final String USER_ID = "userId";

    public static final String ROLE_ID = "roleId";

    public static final String REGISTRY_STATUS = "registryStatus";

    public static final String REGISTRY_ORIGIN = "registryOrigin";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
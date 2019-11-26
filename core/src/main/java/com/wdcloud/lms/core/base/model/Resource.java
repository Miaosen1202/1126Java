package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource")
public class Resource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源所属机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 来源ID，来源有课程、作业、测验、讨论
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 类型，1：课程，2：作业，3：测验，4：讨论
     */
    @Column(name = "origin_type")
    private Integer originType;

    /**
     * 分享人ID、即作者
     */
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String ORG_ID = "orgId";

    public static final String ORIGIN_ID = "originId";

    public static final String ORIGIN_TYPE = "originType";

    public static final String AUTHOR_ID = "authorId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
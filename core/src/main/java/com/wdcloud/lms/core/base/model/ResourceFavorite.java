package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_favorite")
public class ResourceFavorite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 收藏人ID
     */
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String RESOURCE_ID = "resourceId";

    public static final String USER_ID = "userId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
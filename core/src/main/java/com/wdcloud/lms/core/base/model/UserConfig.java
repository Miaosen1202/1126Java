package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_user_config")
public class UserConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户配置
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 手动将帖子标记为已读
     */
    @Column(name = "allow_mark_post_status")
    private Integer allowMarkPostStatus;

    /**
     * 前景色，格式: #666666(日历使用)
     */
    @Column(name = "cover_color")
    private String coverColor;

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

    public static final String ALLOW_MARK_POST_STATUS = "allowMarkPostStatus";

    public static final String COVER_COLOR = "coverColor";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_user_e_portfolio_column")
public class UserEPortfolioColumn implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 电子学档id
     */
    @Column(name = "e_portfolio_id")
    private Long ePortfolioId;

    /**
     * 名称
     */
    private String name;

    /**
     * 电子学档背景色，格式: #666666
     */
    @Column(name = "cover_color")
    private String coverColor;

    /**
     * 位置的横坐标
     */
    private Integer seq;

    /**
     * 预留字段
     */
    private Integer status;

    /**
     * 预留字段
     */
    private Integer type;

    /**
     * 预留字段：可见性, 1: 公开, 2: 仅自己可见，3、朋友可见
     */
    private Integer visibility;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String E_PORTFOLIO_ID = "ePortfolioId";

    public static final String NAME = "name";

    public static final String COVER_COLOR = "coverColor";

    public static final String SEQ = "seq";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String VISIBILITY = "visibility";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
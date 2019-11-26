package com.wdcloud.lms.core.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_user_e_portfolio")
public class UserEPortfolio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 名称
     */
    private String name;

    /**
     * 子页面数量
     */
    @Column(name = "total_page")
    private Integer totalPage;

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

    public static final String USER_ID = "userId";

    public static final String NAME = "name";

    public static final String TOTAL_PAGE = "totalPage";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String VISIBILITY = "visibility";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
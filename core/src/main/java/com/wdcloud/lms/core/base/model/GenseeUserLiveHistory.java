package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "gensee_user_live_history")
public class GenseeUserLiveHistory implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 直播ID
     */
    @Column(name = "gensee_webcast_id")
    private String genseeWebcastId;

    /**
     * 昵称
     */
    @Column(name = "gensee_nickname")
    private String genseeNickname;

    /**
     * 加入时间
     */
    @Column(name = "gensee_join_time")
    private Date genseeJoinTime;

    /**
     * 离开时间
     */
    @Column(name = "gensee_leave_time")
    private Date genseeLeaveTime;

    /**
     * IP地址
     */
    @Column(name = "gensee_ip")
    private String genseeIp;

    /**
     * 用户ID
     */
    @Column(name = "gensee_uid")
    private Long genseeUid;

    /**
     * 区域
     */
    @Column(name = "gensee_area")
    private String genseeArea;

    /**
     * 手机
     */
    @Column(name = "gensee_mobile")
    private String genseeMobile;

    /**
     * 公司
     */
    @Column(name = "gensee_company")
    private String genseeCompany;

    /**
     * 加入终端类型 值说明：  0  PC客户端  1  PC Web端  2  PC Web端(http流)  3  IPAD Web端  4  IPHONE Web端  5  APAD Web端  6  APHONE Web端  7  IPAD APP端  8  IPHONE APP端  9  APAD APP端  10 APHONE APP端  11 MAC 客户端  12  电话端  16  PLAYER SDK  IOS端  17  PLAYER SDK  安卓端  21 小程序IOS端  22 小程序安卓端  23 小程序sdk IOS端  24小程序sdk 安卓端
     */
    @Column(name = "gensee_join_type")
    private Long genseeJoinType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String GENSEE_WEBCAST_ID = "genseeWebcastId";

    public static final String GENSEE_NICKNAME = "genseeNickname";

    public static final String GENSEE_JOIN_TIME = "genseeJoinTime";

    public static final String GENSEE_LEAVE_TIME = "genseeLeaveTime";

    public static final String GENSEE_IP = "genseeIp";

    public static final String GENSEE_UID = "genseeUid";

    public static final String GENSEE_AREA = "genseeArea";

    public static final String GENSEE_MOBILE = "genseeMobile";

    public static final String GENSEE_COMPANY = "genseeCompany";

    public static final String GENSEE_JOIN_TYPE = "genseeJoinType";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
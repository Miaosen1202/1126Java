package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "gensee_user_vod_history")
public class GenseeUserVodHistory implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点播ID
     */
    @Column(name = "gensee_vodId")
    private String genseeVodid;

    /**
     * 用户ID
     */
    @Column(name = "gensee_uid")
    private String genseeUid;

    /**
     * 加入时间
     */
    @Column(name = "gensee_start_time")
    private Date genseeStartTime;

    /**
     * 离开时间
     */
    @Column(name = "gensee_leave_time")
    private Date genseeLeaveTime;

    /**
     * 姓名
     */
    @Column(name = "gensee_name")
    private String genseeName;

    /**
     * 观看时长 单位为毫秒
     */
    @Column(name = "gensee_duration")
    private Integer genseeDuration;

    /**
     * IP地址
     */
    @Column(name = "gensee_ip")
    private String genseeIp;

    /**
     * 区域
     */
    @Column(name = "gensee_area")
    private String genseeArea;

    /**
     * 终端类型 值说明：  0  PC  1  Mac  2  Linux  4  Ipad  8  Iphone  16  Andriod Pad  32  Andriod Phone  132  IPad(PlayerSDK)  136  IPhone(PlayerSDK)  144  Andriod Pad(PlayerSDK)  256  Andriod Phone(PlayerSDK)  0xED  Mobile  237 移动设备（以前版本的移动端的playersdk和app）  21 小程序IOS端  22 小程序安卓端  23 小程序sdk IOS端  24小程序sdk 安卓端  其他值为 Unknown
     */
    @Column(name = "gensee_device")
    private Integer genseeDevice;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String GENSEE_VODID = "genseeVodid";

    public static final String GENSEE_UID = "genseeUid";

    public static final String GENSEE_START_TIME = "genseeStartTime";

    public static final String GENSEE_LEAVE_TIME = "genseeLeaveTime";

    public static final String GENSEE_NAME = "genseeName";

    public static final String GENSEE_DURATION = "genseeDuration";

    public static final String GENSEE_IP = "genseeIp";

    public static final String GENSEE_AREA = "genseeArea";

    public static final String GENSEE_DEVICE = "genseeDevice";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
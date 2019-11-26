package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "gensee_vod")
public class GenseeVod implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点播ID
     */
    @Column(name = "gensee_id")
    private String genseeId;

    /**
     * 主题
     */
    @Column(name = "gensee_subject")
    private String genseeSubject;

    /**
     * 观看口令 允许为空
     */
    @Column(name = "gensee_password")
    private String genseePassword;

    /**
     * 描述
     */
    @Column(name = "gensee_description")
    private String genseeDescription;

    /**
     * 创建时间
     */
    @Column(name = "gensee_created_time")
    private Date genseeCreatedTime;

    /**
     * 加入URL
     */
    @Column(name = "gensee_attendee_join_url")
    private String genseeAttendeeJoinUrl;

    /**
     * 直播ID 指明该点播是由哪个直播录制生成的。
     */
    @Column(name = "gensee_webcast_id")
    private String genseeWebcastId;

    @Column(name = "gensee_screenshot")
    private String genseeScreenshot;

    /**
     * 创建该点播的用户ID (点播的所有者)
     */
    @Column(name = "gensee_creator")
    private String genseeCreator;

    /**
     * 点播的编号
     */
    @Column(name = "gensee_number")
    private String genseeNumber;

    /**
     * 该点播使用的录制件ID
     */
    @Column(name = "gensee_record_id")
    private String genseeRecordId;

    /**
     * 录制开始时间
     */
    @Column(name = "gensee_record_start_time")
    private Date genseeRecordStartTime;

    /**
     * 录制结束时间
     */
    @Column(name = "gensee_record_end_time")
    private Date genseeRecordEndTime;

    /**
     * 0、3、4录制件1多媒体2文档
     */
    @Column(name = "gensee_gr_type")
    private Integer genseeGrType;

    /**
     * 内容时长 单位是毫秒。
     */
    @Column(name = "gensee_duration")
    private Long genseeDuration;

    /**
     * 0转换中 1转换成功 -1 失败
     */
    @Column(name = "gensee_convert_result")
    private Integer genseeConvertResult;

    /**
     * 演讲人信息
     */
    @Column(name = "gensee_speaker_info")
    private String genseeSpeakerInfo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String GENSEE_ID = "genseeId";

    public static final String GENSEE_SUBJECT = "genseeSubject";

    public static final String GENSEE_PASSWORD = "genseePassword";

    public static final String GENSEE_DESCRIPTION = "genseeDescription";

    public static final String GENSEE_CREATED_TIME = "genseeCreatedTime";

    public static final String GENSEE_ATTENDEE_JOIN_URL = "genseeAttendeeJoinUrl";

    public static final String GENSEE_WEBCAST_ID = "genseeWebcastId";

    public static final String GENSEE_SCREENSHOT = "genseeScreenshot";

    public static final String GENSEE_CREATOR = "genseeCreator";

    public static final String GENSEE_NUMBER = "genseeNumber";

    public static final String GENSEE_RECORD_ID = "genseeRecordId";

    public static final String GENSEE_RECORD_START_TIME = "genseeRecordStartTime";

    public static final String GENSEE_RECORD_END_TIME = "genseeRecordEndTime";

    public static final String GENSEE_GR_TYPE = "genseeGrType";

    public static final String GENSEE_DURATION = "genseeDuration";

    public static final String GENSEE_CONVERT_RESULT = "genseeConvertResult";

    public static final String GENSEE_SPEAKER_INFO = "genseeSpeakerInfo";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
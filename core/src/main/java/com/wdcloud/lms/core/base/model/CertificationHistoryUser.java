package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_certification_history_user")
public class CertificationHistoryUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 证书ID
     */
    @Column(name = "certification_id")
    private Long certificationId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 证书有效期开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 有效期截止时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 周期序号
     */
    @Column(name = "history_no")
    private Integer historyNo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String CERTIFICATION_ID = "certificationId";

    public static final String USER_ID = "userId";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String HISTORY_NO = "historyNo";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_org_email")
public class OrgEmail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 跟机构代码
     */
    @Column(name = "root_org_code")
    private String rootOrgCode;

    /**
     * 邮件默认名称
     */
    @Column(name = "email_display_name")
    private String emailDisplayName;

    /**
     * smtp host
     */
    @Column(name = "email_host")
    private String emailHost;

    /**
     * 端口号
     */
    @Column(name = "emai_port")
    private Integer emaiPort;

    /**
     * 加密类型 0:none,1:ssl,2:tls
     */
    @Column(name = "emai_security_type")
    private Integer emaiSecurityType;

    /**
     * 授权类型 0:login
     */
    @Column(name = "emai_auth_type")
    private Integer emaiAuthType;

    /**
     * 邮箱用户名
     */
    @Column(name = "email_user_name")
    private String emailUserName;

    /**
     * 邮箱授权码
     */
    @Column(name = "email_pwd")
    private String emailPwd;

    /**
     * 是否开通
     */
    @Column(name = "is_open")
    private Integer isOpen;

    /**
     * 是否开通注册
     */
    @Column(name = "is_open_regist")
    private Integer isOpenRegist;

    /**
     * 是否开通找回密码
     */
    @Column(name = "is_open_retrieve_pwd")
    private Integer isOpenRetrievePwd;

    /**
     * 是否开通邮件通知
     */
    @Column(name = "is_open_push_notify")
    private Integer isOpenPushNotify;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String ROOT_ORG_CODE = "rootOrgCode";

    public static final String EMAIL_DISPLAY_NAME = "emailDisplayName";

    public static final String EMAIL_HOST = "emailHost";

    public static final String EMAI_PORT = "emaiPort";

    public static final String EMAI_SECURITY_TYPE = "emaiSecurityType";

    public static final String EMAI_AUTH_TYPE = "emaiAuthType";

    public static final String EMAIL_USER_NAME = "emailUserName";

    public static final String EMAIL_PWD = "emailPwd";

    public static final String IS_OPEN = "isOpen";

    public static final String IS_OPEN_REGIST = "isOpenRegist";

    public static final String IS_OPEN_RETRIEVE_PWD = "isOpenRetrievePwd";

    public static final String IS_OPEN_PUSH_NOTIFY = "isOpenPushNotify";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
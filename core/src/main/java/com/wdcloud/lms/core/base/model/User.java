package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 机构tree_id
     */
    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称，用于讨论、消息、评论等
     */
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * 用户全称，用于评分展示(由 first_name及 last_name拼接)
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 性别, 0: 未知, 1: 男, 2: 女
     */
    private Integer sex;

    /**
     * SIS ID
     */
    @Column(name = "sis_id")
    private String sisId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 头像文件：关联sys_user_file表主键
     */
    @Column(name = "avatar_user_file_id")
    private Long avatarUserFileId;

    /**
     * 头像文件
     */
    @Column(name = "avatar_file_id")
    private String avatarFileId;

    /**
     * 是否注册中用户,注册中用户为课程用户添加时的临时用户,不能正常使用
     */
    @Column(name = "is_registering")
    private Integer isRegistering;

    /**
     * 使用语言（预留）
     */
    private String language;

    /**
     * 时区（预留）
     */
    @Column(name = "time_zone")
    private String timeZone;

    /**
     * 最近登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 头衔
     */
    private String title;

    /**
     * 启用状态
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 简介
     */
    private String description;

    public static final String ID = "id";

    public static final String ORG_ID = "orgId";

    public static final String ORG_TREE_ID = "orgTreeId";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String NICKNAME = "nickname";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String FULL_NAME = "fullName";

    public static final String SEX = "sex";

    public static final String SIS_ID = "sisId";

    public static final String EMAIL = "email";

    public static final String PHONE = "phone";

    public static final String AVATAR_USER_FILE_ID = "avatarUserFileId";

    public static final String AVATAR_FILE_ID = "avatarFileId";

    public static final String IS_REGISTERING = "isRegistering";

    public static final String LANGUAGE = "language";

    public static final String TIME_ZONE = "timeZone";

    public static final String LAST_LOGIN_TIME = "lastLoginTime";

    public static final String TITLE = "title";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
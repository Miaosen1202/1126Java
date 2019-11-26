package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_login_record")
public class LoginRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 登录用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP
     */
    private String ip;

    @Column(name = "create_time")
    private Date createTime;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String USERNAME = "username";

    public static final String IP = "ip";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}
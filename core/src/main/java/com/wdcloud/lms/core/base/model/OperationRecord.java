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
@Table(name = "sys_operation_record")
public class OperationRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 路径
     */
    private String path;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 请求体
     */
    private String body;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String PATH = "path";

    public static final String CREATE_TIME = "createTime";

    public static final String BODY = "body";

    private static final long serialVersionUID = 1L;
}
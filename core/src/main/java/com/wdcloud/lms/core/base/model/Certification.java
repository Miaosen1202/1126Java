package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_certification")
public class Certification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 认证名称
     */
    private String name;

    /**
     * 操作截止天数
     */
    @Column(name = "op_day")
    private Integer opDay;

    /**
     * 认证类型 0：永久 1: 周期 
     */
    private Integer type;

    /**
     * 证件有效月份（单位月）
     */
    private Integer validity;

    /**
     * 发行机构 0：Internal 1：External
     */
    private Integer issuer;

    /**
     * 封面图ID
     */
    @Column(name = "cover_img_id")
    private Long coverImgId;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 认证状态 0:草稿 1：已发布 2：已注销
     */
    private Integer status;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 描述
     */
    private String memo;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String OP_DAY = "opDay";

    public static final String TYPE = "type";

    public static final String VALIDITY = "validity";

    public static final String ISSUER = "issuer";

    public static final String COVER_IMG_ID = "coverImgId";

    public static final String PUBLISH_TIME = "publishTime";

    public static final String STATUS = "status";

    public static final String IS_DELETE = "isDelete";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String MEMO = "memo";

    private static final long serialVersionUID = 1L;
}
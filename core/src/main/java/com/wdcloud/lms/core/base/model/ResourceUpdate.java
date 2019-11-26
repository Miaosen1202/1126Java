package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_update")
public class ResourceUpdate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 版本ID
     */
    @Column(name = "version_id")
    private Long versionId;

    /**
     * 版权
     */
    private Integer licence;

    /**
     * 分级信息: 0~15 每个年级占对应索引的一个bit e.g.: 5~8年级 二进制为:100100000 十进制为:288.\r\n  查询时年级对应索引位置1 按位&操作 e.g.: 查询8年级 where grade_id & 256
     */
    private Integer grade;

    /**
     * 资源被导入的次数
     */
    @Column(name = "import_count")
    private Integer importCount;

    /**
     * 分享范围，1：自己，2：机构，3：公开
     */
    @Column(name = "share_range")
    private Integer shareRange;

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
    private String description;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String RESOURCE_ID = "resourceId";

    public static final String VERSION_ID = "versionId";

    public static final String LICENCE = "licence";

    public static final String GRADE = "grade";

    public static final String IMPORT_COUNT = "importCount";

    public static final String SHARE_RANGE = "shareRange";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "res_resource_file")
public class ResourceFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 资源类型，1：封面，2：数据，3：附件
     */
    private Integer type;

    /**
     * 版本ID
     */
    @Column(name = "version_id")
    private Long versionId;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件大小, 单位byte
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 路径
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 缩略图
     */
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    /**
     * 缩略图来源，1：上传，2：文件内获取（视频、图片），3：资源库获取
     */
    @Column(name = "thumbnail_origin_type")
    private Integer thumbnailOriginType;

    /**
     * 缩略图来源ID（如果引用的资源库文件）
     */
    @Column(name = "thumbnail_origin_id")
    private Long thumbnailOriginId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String RESOURCE_ID = "resourceId";

    public static final String TYPE = "type";

    public static final String VERSION_ID = "versionId";

    public static final String FILE_NAME = "fileName";

    public static final String FILE_TYPE = "fileType";

    public static final String FILE_SIZE = "fileSize";

    public static final String FILE_URL = "fileUrl";

    public static final String THUMBNAIL_URL = "thumbnailUrl";

    public static final String THUMBNAIL_ORIGIN_TYPE = "thumbnailOriginType";

    public static final String THUMBNAIL_ORIGIN_ID = "thumbnailOriginId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
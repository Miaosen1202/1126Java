package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_user_file")
public class UserFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件所属空间, 1: 课程 2: 小组 3: 个人(不同空间有不同的配额)
     */
    @Column(name = "space_type")
    private Integer spaceType;

    /**
     * 用来描述二级文件夹用途，　0: other 1: unfiled（课程内容相关附件，用户讨论、公告回复附件，小组内容相关附件）2: profile(存放用户头像文件) 3: submission（存放用户作业回答附件）
     */
    @Column(name = "dir_usage")
    private Integer dirUsage;

    /**
     * 文件所属课程
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 文件所属学习小组
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 文件所属用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否为文件夹
     */
    @Column(name = "is_directory")
    private Integer isDirectory;

    /**
     * 是否系统级别目录（教师学生不能删除）
     */
    @Column(name = "is_system_level")
    private Integer isSystemLevel;

    /**
     * 文件夹是否允许用户单独上传文件与创建文件夹
     */
    @Column(name = "allow_upload")
    private Integer allowUpload;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件类型（文件大类型：图片、音频、视频、文档...）
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件大小，单位byte
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 文件URL
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 树ID
     */
    @Column(name = "tree_id")
    private String treeId;

    /**
     * 父id，根节点为-1
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 学生访问策略, 1: 无限制 2: 只能通过链接查询 3: 限制访问时间
     */
    @Column(name = "access_strategy")
    private Integer accessStrategy;

    /**
     * 访问开始时间（access_strategy=3）
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 访问结束时间（access_strategy=3）
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 发布状态
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

    public static final String ID = "id";

    public static final String SPACE_TYPE = "spaceType";

    public static final String DIR_USAGE = "dirUsage";

    public static final String COURSE_ID = "courseId";

    public static final String STUDY_GROUP_ID = "studyGroupId";

    public static final String USER_ID = "userId";

    public static final String IS_DIRECTORY = "isDirectory";

    public static final String IS_SYSTEM_LEVEL = "isSystemLevel";

    public static final String ALLOW_UPLOAD = "allowUpload";

    public static final String FILE_NAME = "fileName";

    public static final String FILE_TYPE = "fileType";

    public static final String FILE_SIZE = "fileSize";

    public static final String FILE_URL = "fileUrl";

    public static final String TREE_ID = "treeId";

    public static final String PARENT_ID = "parentId";

    public static final String ACCESS_STRATEGY = "accessStrategy";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}
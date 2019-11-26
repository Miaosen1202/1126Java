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
@Table(name = "cos_course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sis_id")
    private String sisId;

    /**
     * 名称
     */
    private String name;

    /**
     * 课程编码
     */
    private String code;

    /**
     * 发布状态
     */
    private Integer status;

    /**
     * 最近发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 课程封面ID
     */
    @Column(name = "cover_file_id")
    private Long coverFileId;

    /**
     * 预留字段
     */
    private Integer type;

    /**
     * 可见性, 1: 公开, 2: 课程专属
     */
    private Integer visibility;

    /**
     * 学期
     */
    @Column(name = "term_id")
    private Long termId;

    /**
     * 课程进度，1: 未开始 2: 进行中 3: 已结束
     */
    private Integer progress;

    /**
     * 创建方式, 1: 导入  2: 教师创建
     */
    @Column(name = "create_mode")
    private Integer createMode;

    /**
     * 开始时间（优先学期设置）
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间（优先学期设置）
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 课程首页：ACTIVE_STREAM, MODULE, ASSIGNMENTS, SYLLABUS, PAGE
     */
    private String homepage;

    /**
     * 是否结束
     */
    @Column(name = "is_concluded")
    private Integer isConcluded;

    /**
     * 关闭时间
     */
    @Column(name = "concluded_time")
    private Date concludedTime;

    /**
     * 是否包含到公共课程索引
     */
    @Column(name = "include_publish_index")
    private Integer includePublishIndex;

    /**
     * 允许学生直接加入
     */
    @Column(name = "allow_join")
    private Integer allowJoin;

    /**
     * 机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "org_tree_id")
    private String orgTreeId;

    /**
     * 语言
     */
    private String language;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 0：评分时不涉及权重；1：评分时涉及权重
     */
    @Column(name = "is_weight_grade")
    private Integer isWeightGrade;

    /**
     * 描述
     */
    private String description;

    public static final String ID = "id";

    public static final String SIS_ID = "sisId";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String STATUS = "status";

    public static final String PUBLISH_TIME = "publishTime";

    public static final String COVER_FILE_ID = "coverFileId";

    public static final String TYPE = "type";

    public static final String VISIBILITY = "visibility";

    public static final String TERM_ID = "termId";

    public static final String PROGRESS = "progress";

    public static final String CREATE_MODE = "createMode";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String HOMEPAGE = "homepage";

    public static final String IS_CONCLUDED = "isConcluded";

    public static final String CONCLUDED_TIME = "concludedTime";

    public static final String INCLUDE_PUBLISH_INDEX = "includePublishIndex";

    public static final String ALLOW_JOIN = "allowJoin";

    public static final String ORG_ID = "orgId";

    public static final String ORG_TREE_ID = "orgTreeId";

    public static final String LANGUAGE = "language";

    public static final String IS_DELETED = "isDeleted";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String IS_WEIGHT_GRADE = "isWeightGrade";

    public static final String DESCRIPTION = "description";

    private static final long serialVersionUID = 1L;
}
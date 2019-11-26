package com.wdcloud.lms.core.base.vo.resource;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class ResourceVO<T> {

    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 版本
     */
    private Date version;

    /**
     * 版本
     */
    private String versionNotes;

    /**
     * 是否有新的版本信息 0; 没有，1：有
     */
    private Integer hasNewNote;

    private T resource;

    /**
     * 资源类型
     */
    private Integer originType;

    /**
     * 导入数量
     */
    private Integer importCount;

    /**
     * 收藏的数量
     */
    private Integer favoriteCount;

    /**
     * 版权
     */
     private Integer licence;

    /**
     * 是否是我喜爱的资源
     */
    private Integer isFavorite;

    /**
     * 缩略图url
     */
     private String thumbnailUrl;

    /**
     * 可做的操作
     */
     private Integer operation;

    /**
     * 上传者名字
     */
     private String author;

    /**
     * 上传者id
     */
    private Long authorId;

    /**
     * 机构名字
     */
     private String institution;

    /**
     * 年级开始
     */
     private Integer gradeStart;

    /**
     * 年级结束
     */
     private Integer gradeEnd;

    /**
     * 简介
     */
     private String description;

    /**
     * 标签
     */
     private List<String> tags;

    /**
     * 分享范围
     */
     private Integer shareRange;

    /**
     * 是否有未查看的日志
     */
    private Integer hasCheck;

    /**
     * 年级
     */
    private Integer grade;

    private Integer operationType;

}

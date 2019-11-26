package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

@Data
public class DiscussionResourceShareDTO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 1: 普通讨论， 2： 小组讨论
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件文件ID
     */
    private Long attachmentFileId;

    /**
     * 是否评分
     */
    private Integer isGrade;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分
     */
    private Integer gradeType;

    /**
     * 评分方案
     */
    private Long gradeSchemeId;

}

package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

/**
 * @author zhangxutao
 *
 */
@Data
public class GradeEditVo {
    /**
     * 评分id
     */
    private Long id;
    private Long courseId;
    private String assignmentGroupItemId;
    /**
     * 任务类型： 1: 作业 2: 讨论 3: 测验',
     */
    private Integer originType;
    private Long studyGroupId;
    /**
     * 个人或小组类型
     */
    private Integer releaseType;
    private Long originId;
    private String content;
    /**
     * 评论用户
     */
    private Long userId;
    /**
     * 用户的类型: 0是小组 1是个人
     */
    private Integer userType;
    /**
     * 小组作业时，评论是否组成员可见
     */
    private Integer isSendGroupUser;
    /**
     * 总分
     */
    private String score;
    /**
     * 得分
     */
    private String gradeScore;
    /**
     * 单一或批量 1：单一 2：批量
     */
    private Integer gradeType;
    /**
     * 是否覆盖之前的评分 0不覆盖  1覆盖
     */
    private Integer isOverWrite;
    /**
     * 设置最低评分 0不设置  1设置
     */
    private Integer isSetLowestScore;
    /**
     * 提交时间-多时间【作业、测验】单时间【讨论】
     */
    private Long submitTime;

    /**
     * 评分方式 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分'【新增】
     */
    private Integer showScoreType;
}

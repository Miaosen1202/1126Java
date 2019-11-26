package com.wdcloud.lms.business.grade.vo;
import lombok.Data;

/**
 * @author zhangxutao
 */
@Data
public class GradeCommentEditVo {
    private Long id;
    private Long courseId;
    private String assignmentGroupItemId;
    /**
     *  任务类型： 1: 作业 2: 讨论 3: 测验',
     */
    private Integer originType;
    private Long studyGroupId;
    /**
     * 个人或小组类型
     */
    private Integer releaseType;
    /**
     * 用户的类型: 0是小组 1是个人 2：all【全部】
     */
    private Integer userType;
    private Long originId;
    private String content;
    /**
     * 小组作业时，评论是否组成员可见
     */
    private Integer isSendGroupUser;
    /**
     * 单一或批量 1：单一 2：批量
     */
    private Integer gradeType;
    private Long userId;
    /**
     * 设置最低评分 0不设置  1设置
     */
    private Integer isSetLowestScore;

}

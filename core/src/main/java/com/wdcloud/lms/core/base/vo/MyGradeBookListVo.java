package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxutao
 *
 */
@Data
public class MyGradeBookListVo {
    private Long id;
    private Long originId;
    private Integer originType;
    private String title;
    private String submissionStatus;
    private String scores;
    private String byPercentLetter;
    private Date dueTime;
    private Long userId;
    private Date subTime;//最后提交时间
    private String missionGroup;//任务组名称
    private Integer includeGrade;//此任务是否计入总分
    private String gradeCount;//评论数量

    private Integer showScoreType; //得分显示类型 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
    private String showScoreTag; //得分显示标签

    private Double gradeScore; //每项任务得分
    private Double gradeScoreFull; //每项任务满分
    private Double weight; //权重 默认100
}

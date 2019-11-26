package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxutao
 */
@Data
public class MyGradeBookVo {
    private Long id;
    private Long originId;
    private Integer originType;
    private String title;
    private String submissionStatus;
    private String scores;
    private String byPercentLetter;
    private String ranking;
    private String gradeCount;

    private Date dueTime;
    private Date subTime;
    private String missionGroup;
    private Long userId;
    private Integer includeGrade;//此任务是否计入总分


}

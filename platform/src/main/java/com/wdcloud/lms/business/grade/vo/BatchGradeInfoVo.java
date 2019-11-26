package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

/**
 * @author zhangxutao
 *
 */
@Data
public class BatchGradeInfoVo {
    private Integer group;
    private Integer students;
    private Integer unG;
    private Integer graded;
    private Integer unSub;
    private Integer Submitted;
    private String pointsPossible;
    private String averageScore;
    private String highScore;
    private String lowScore;
    private String totalStu;

}

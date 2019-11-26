package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.User;
import lombok.Data;

@Data
public class UserByIdVo extends User {
    /*
    * 课程名称
    * */
    private String courseName;

    /*
     * 用户头像路径
     * */
    private String fileUrl;

    /*
     * 逾期提交
     * */
    private Integer overdue;

    /*
     * 未逾期提交
     * */
    private Integer unOverdue;

    /*
     * 用户下所有任务的得分
     * */
    private Integer gradeScore;

    /*
     * 用户下所有任务的总分
     * */
    private Integer score;

    /*
     * 得分/总分的比例
     * */
    private Double proportionScore;

}

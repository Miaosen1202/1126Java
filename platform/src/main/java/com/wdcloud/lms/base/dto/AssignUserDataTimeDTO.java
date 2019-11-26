package com.wdcloud.lms.base.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AssignUserDataTimeDTO {
    /**
     * 课程开始日期
     */
    private Date courseStartTime;

    /**
     * 课程结束日期
     */

    private Date courseEndTime ;

    /**
     * 学期开始日期
     */
    private Date termStartTime;

    /**
     * 学期结束日期
     */

    private Date termEndTime;
    /**
     * 班级开始日期
     */
    private Date sectionStartTime;

    /**
     * 班级结束日期
     */

    private Date sectionEndTime;

    /**
     * 用户开始日期
     */
    private Date userStartTime;

    /**
     * 用户结束日期
     */

    private Date userEndTime;
    /**
     * 用户规定截止日期
     */

    private Date userLimitTime;
}

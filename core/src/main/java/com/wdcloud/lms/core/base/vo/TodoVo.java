package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class TodoVo {
    //待打分
    private Long courseId;
    private String courseName;
    private Long originType;
    private Long originId;
    private String title;
    private Long score;
    private Long toBeScoredTotal;
    //数据类型 1打分任务 2日历todo 3待提交任务
    private Long dataType;
    //mytodo
    private Long calendarType;
    private Long userId;
    private String userName;
    private Date doTime;

    //待提交任务
    private Assign assign;

    //排序时间依据
    private Date orderByTime;

    private Date startTime;
    private Date limitTime;
    private Date endTime;
    //是due还是close  0:due 1:close
    private Long dueOrClose;
    //是否迟交
    private Long isLated;

}

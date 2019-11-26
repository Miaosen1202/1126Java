package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WarningVO {
    /**
     * 0:不显示
     * 1:Not available until xxx xxx xx,xxxx at xx:xx am/pm    例如 Not available until Jan 4, 2019 at 8:59am
     * 2:Available until xxx xxx xx,xxxx at xx:xx am/pm        例如 Available until Jan 4, 2019 at 8:59am
     * 3:Was locked at xxx xx at xx：xx am/pm                  例如 Was locked at Dec 16 at 11:59 pm
     */
    private Integer warningType;
    private Date warningTime;
}

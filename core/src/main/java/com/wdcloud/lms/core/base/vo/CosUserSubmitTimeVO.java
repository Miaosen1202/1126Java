package com.wdcloud.lms.core.base.vo;

/**
 * @author zhangxutao
 */

import lombok.Data;

import java.util.Date;

@Data
public class CosUserSubmitTimeVO {
    private Date limitTime;
    private Long userId;
    private Date lastSubmitTime;
    private Integer submitCount;
    private Integer isGrade;
    private Integer needGrade;
    private Integer isOverdue;
}

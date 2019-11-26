package com.wdcloud.lms.core.base.vo;

import lombok.Data;
import java.util.Date;


@Data
public class GradeCosUserSubmitTimeVo {
    private Date limitTime;
    private Long userId;
    private Date lastSubmitTime;
    private Integer submitCount;
    private Integer isGraded;
    private Integer needGrade;
    private Integer isOverdue;
}

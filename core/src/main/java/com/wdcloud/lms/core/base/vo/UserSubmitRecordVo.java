package com.wdcloud.lms.core.base.vo;


import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import lombok.Data;

import java.util.Date;
@Data
public class UserSubmitRecordVo extends UserSubmitRecord {
    private Integer graderIdcount;//评分用户个数
    private Integer unGraderIdcount;//未评分用户个数
    private Date endTime;//任务结束时间
    private Long assignUserId;//任务id
    private Date startTime;//任务开始时间
}

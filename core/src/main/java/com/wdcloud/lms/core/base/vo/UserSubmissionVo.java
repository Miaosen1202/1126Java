package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import lombok.Data;

import java.util.Date;

@Data
public class UserSubmissionVo extends AssignmentGroupItem {
    private Long courseId;
    private String groupName;
    private String userId;
    private Date startTime;
    private Date endTime;
    private Date limitTime;
    private Date submitTime;
    private Integer isOverdue;
    private Integer gradeScore;
    private Integer isGraded;
}

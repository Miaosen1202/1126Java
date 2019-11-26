package com.wdcloud.lms.business.assignment.vo;

import com.wdcloud.lms.core.base.model.AssignmentReply;
import com.wdcloud.lms.core.base.model.UserFile;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentReplyVO extends AssignmentReply {
    private List<UserFile> attachments;//附件
    private Integer score;//老师打的分
    private Boolean isLate;
    private String username;//提交人用户名

}

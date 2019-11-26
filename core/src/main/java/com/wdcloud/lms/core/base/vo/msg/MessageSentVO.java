package com.wdcloud.lms.core.base.vo.msg;

import lombok.Data;

@Data
public class MessageSentVO{

    private Long sendId;
    private Long subjectId;
    private String messageSubject;
    private Long courseId;
    private String courseName;
    private Long msgTotal;

    private MsgItemVO msgItemVO;
}

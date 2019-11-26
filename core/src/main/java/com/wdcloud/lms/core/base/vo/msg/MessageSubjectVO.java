package com.wdcloud.lms.core.base.vo.msg;

import lombok.Data;

@Data
public class MessageSubjectVO {
    private Long sendId;
    private Long recId;
    private Long subjectId;
    private String messageSubject;
    private Long courseId;
    private String courseName;
    //主题是否收藏过
    private Long isStar;
    private Long msgTotal;

    private MsgItemVO msgItemVO;
}

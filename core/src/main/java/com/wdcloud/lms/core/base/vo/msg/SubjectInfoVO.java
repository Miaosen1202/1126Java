package com.wdcloud.lms.core.base.vo.msg;

import com.wdcloud.lms.core.base.model.Message;
import lombok.Data;

@Data
public class SubjectInfoVO extends Message {
    private String courseName;
    private Integer isStar;
}

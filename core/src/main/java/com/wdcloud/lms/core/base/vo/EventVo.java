package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Event;
import lombok.Data;

@Data
public class EventVo extends Event {
    private String userName;
    private String courseName;
    private String studyGroupName;
}

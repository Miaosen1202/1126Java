package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssignmentGroupItemChangeOpTypeEnum {

    CREATE(1),
    UPDATE_CONTENT(2),
    UPDATE_START_TIME(3),
    UPDATE_END_TIME(4),
    UPDATE_DUE_TIME(5)
    ;

    private Integer type;
}

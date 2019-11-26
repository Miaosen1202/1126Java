package com.wdcloud.lms.business.discussion.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 作业小组内任务项
 * '任务类型： 1: 作业 2: 讨论 3: 测验 4: 外部工具作业 5: 不计分作业'
 */
@Getter
@AllArgsConstructor
public enum AssignmentGroupItemOriginTypeEnum {
    ASSIGNMENT(1),
    DISCUSSION(2),
    QUIZ(3),
    OUTUTILASSIGNMENT(4),
    NOSCOREASSIGNMENT(5),
    ;

    private Integer type;

    public static AssignmentGroupItemOriginTypeEnum typeOf(Integer type) {
        for (AssignmentGroupItemOriginTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum AssignmentReplyTypeEnum {
    /**
     * 文本
     */
    TEXT(1),
    /**
     * 文件
     */
    FILE(2),
    /**
     * URL
     */
    URL(3),
    /**
     * 媒体
     */
    MATE(4);


    private Integer type;

    public static AssignmentReplyTypeEnum typeOf(Integer type) {
        for (AssignmentReplyTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

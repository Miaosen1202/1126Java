package com.wdcloud.lms.business.discussion.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 1: 普通讨论， 2： 小组讨论
 */
@Getter
@AllArgsConstructor
public enum DiscussionTypeEnum {
    NORMAL(1),
    GROUP(2);

    private Integer type;

    public static DiscussionTypeEnum typeOf(Integer type) {
        for (DiscussionTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

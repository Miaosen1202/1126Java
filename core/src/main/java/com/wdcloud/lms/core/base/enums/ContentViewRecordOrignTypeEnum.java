package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 类型，1：讨论话题 2：讨论回复 3：公告话题 4：公告回复
 */
@Getter
@AllArgsConstructor
public enum ContentViewRecordOrignTypeEnum {
    DISCUSSION_TOPIC(1),
    DISCUSSION_REPLY(2),
    ANNOUNCE_TOPIC(3),
    ANNOUNCE_REPLY(4);

    private Integer type;

    public static ContentViewRecordOrignTypeEnum typeOf(Integer type) {
        for (ContentViewRecordOrignTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

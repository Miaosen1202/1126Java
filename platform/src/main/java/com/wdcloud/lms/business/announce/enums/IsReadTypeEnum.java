package com.wdcloud.lms.business.announce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:默认所有
 */
@Getter
@AllArgsConstructor
public enum IsReadTypeEnum {
    NOTREAD(0),
    READ(1),
    ALL(2);

    private Integer type;

    public static IsReadTypeEnum typeOf(Integer type) {
        for (IsReadTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

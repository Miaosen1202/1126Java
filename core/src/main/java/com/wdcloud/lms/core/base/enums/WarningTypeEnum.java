package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum WarningTypeEnum {
    NONE(0),
    NOT_AVAILABLE(1),
    AVAILABLE(2),
    LOCKED(3);

    private int type;

    public static WarningTypeEnum statusOf(Integer type) {
        for (WarningTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }

        return null;
    }
}

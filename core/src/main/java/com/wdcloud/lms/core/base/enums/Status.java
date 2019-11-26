package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Status {
    YES(1),
    NO(0);

    private int status;

    public static Status statusOf(Integer status) {
        for (Status value : values()) {
            if (Objects.equals(value.status, status)) {
                return value;
            }
        }

        return null;
    }
}

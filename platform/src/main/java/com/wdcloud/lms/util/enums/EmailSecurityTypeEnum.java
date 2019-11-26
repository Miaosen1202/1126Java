package com.wdcloud.lms.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 加密类型 0:none,1:ssl,2:tls
 */
@Getter
@AllArgsConstructor
public enum EmailSecurityTypeEnum {
    NONE(0),
    SSL(1),
    TLS(2);

    private Integer value;

    public static EmailSecurityTypeEnum valueOf(Integer type) {
        for (EmailSecurityTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

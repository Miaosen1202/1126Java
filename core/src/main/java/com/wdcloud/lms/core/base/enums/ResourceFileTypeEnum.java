package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 资源文件类型
 */
@Getter
@AllArgsConstructor
public enum ResourceFileTypeEnum {

    COVER(1),

    DATA(2),

    ATTACHMENT(3);

    private Integer type;

    public static ResourceFileTypeEnum typeOf(Integer type) {
        for (ResourceFileTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

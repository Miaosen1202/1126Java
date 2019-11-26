package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ModuleStatusEnum {

    /**
     * 未完成
     */
    INCOMPLETE(0),

    /**
     * 完成
     */
    COMPLETE(1);


    private Integer type;

    public static ModuleStatusEnum typeOf(Integer type) {
        for (ModuleStatusEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

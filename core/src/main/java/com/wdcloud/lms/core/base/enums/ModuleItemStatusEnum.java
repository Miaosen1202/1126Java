package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ModuleItemStatusEnum {

    /**
     * 未完成
     */
    INCOMPLETE(0),

    /**
     * 完成
     */
    COMPLETE(1);


    private Integer type;

    public static ModuleItemStatusEnum typeOf(Integer type) {
        for (ModuleItemStatusEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

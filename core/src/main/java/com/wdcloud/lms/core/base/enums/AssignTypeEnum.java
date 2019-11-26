package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum AssignTypeEnum {

    /**
     * 所有
     */
    ALL(1),

    /**
     * 班级
     */
    SECTION(2),

    /**
     * 用户
     */
    USER(3);

    private Integer type;

    public static AssignTypeEnum typeOf(Integer type) {
        for (AssignTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

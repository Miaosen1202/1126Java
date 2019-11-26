package com.wdcloud.lms.business.calender.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 复制间隔单位 0:day 1:week 2:month
 */
@Getter
@AllArgsConstructor
public enum EveryUnitEnum {
    DAY(0),
    WEEK(1),
    MONTH(2);

    private Integer value;

    public static EveryUnitEnum valueOf(Integer type) {
        for (EveryUnitEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

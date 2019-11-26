package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 *  日历类型, 1: 个人 2: 课程 3: 学习小组
 */
@Getter
@AllArgsConstructor
public enum CalendarTypeEnum {
    PERSON(1),
    COURSE(2),
    STYDYGROUP(3);

    private Integer value;

    public static CalendarTypeEnum valueOf(Integer type) {
        for (CalendarTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

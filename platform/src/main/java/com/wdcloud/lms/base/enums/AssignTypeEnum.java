package com.wdcloud.lms.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 功能：分配类型枚举类
 * 黄建林
 *
 */
@Getter
@AllArgsConstructor
public enum AssignTypeEnum {
    /**
     * 所有
     */
    EVERYONE(1),

    /**
     * 班级
     */
    COURSESECTION(2),

    /**
     * 分配到具体学生
     */
    STUDENT(3),

    /**
     * 分配到小组
     */
    GROUPS(4);

    private Integer type;

    public static AssignTypeEnum typeOf(Integer type) {
        for (AssignTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return EVERYONE;
    }
}

package com.wdcloud.lms.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 功能：用户分配状态
 * 黄建林
 */
@Getter
@AllArgsConstructor
public enum AssignStatusEnum {
    /**
     * 没有权限
     */
    NOAUTHORITY(0),

    /**
     * 当前时间还没达到规定的开始时间
     */
    NOTBEGIN(1),

    /**
     * 当前时间已经超过了截止时间
     */
    EXCEEDEDDEADLINE(2),

    /**
     * 当前时间已经超过了结束时间
     */
    EXCEEDEDENDTIME(3),
    /**
     * 当前时间限制都为空，没有任何时间限制，任何时候都有权限；
     */
    UNLIMITED(4),
    /**
     * 当前时间在规定限定的时间范围内
     */
    NORMAL(5);

    private Integer status;

    public static AssignStatusEnum typeOf(Integer status) {
        for (AssignStatusEnum statusEnum : values()) {
            if (Objects.equals(statusEnum.status, status)) {
                return statusEnum;
            }
        }
        return NOAUTHORITY;
    }
}

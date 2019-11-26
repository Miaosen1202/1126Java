package com.wdcloud.lms.business.calender.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
  注册状态, 1: 邀请中, 2: 加入
 */
@Getter
@AllArgsConstructor
public enum RegistryStatusEnum {
    /**
     * 邀请中
     */
    PENDING(1),

    /**
     * 加入
     */
    JOINED(2);

    private Integer value;

    public static RegistryStatusEnum valueOf(Integer type) {
        for (RegistryStatusEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum GenseeRoleEnum {
    /**
     * 管理员
     */
    ADMIN("admin"),

    /**
     * 组织者
     */
    ORGANIZER("organizer"),

    /**
     * 普通用户
     */
    USER("user");

    private String type;

    public static GenseeRoleEnum typeOf(String type) {
        for (GenseeRoleEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

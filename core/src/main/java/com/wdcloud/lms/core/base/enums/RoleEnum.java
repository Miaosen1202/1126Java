package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(1L, "Admin"),

    /**
     * 教师
     */
    TEACHER(2L, "Teacher"),
    /**
     * 助教
     */
    TA(3L, "Ta"),

    /**
     * 学生
     */
    STUDENT(4L, "Student");

    private Long type;
    private String name;
    private static LinkedHashMap<Long, String> roleIdName = new LinkedHashMap<>();

    static {
        for (RoleEnum typeEnum : values()) {
            roleIdName.put(typeEnum.type, typeEnum.name);
        }
    }

    public static Map<Long, String> getRoleIdAndName() {
        return roleIdName;
    }

    public static RoleEnum typeOf(Integer type) {
        for (RoleEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

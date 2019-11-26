package com.wdcloud.lms.business.sis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SisRoleEnum {
    STUDENT("student"),
    TEACHER("teacher"),
    TA("ta"),
    OBSERVER("observer");

    private String name;

    public static SisRoleEnum nameOf(String name) {
        for (SisRoleEnum roleEnum : values()) {
            if (Objects.equals(roleEnum.name, name)) {
                return roleEnum;
            }
        }

        return null;
    }
}

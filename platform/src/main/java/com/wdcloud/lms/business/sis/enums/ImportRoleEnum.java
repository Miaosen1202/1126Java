package com.wdcloud.lms.business.sis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ImportRoleEnum {
    STUDENT("student"),
    TEACHER("teacher"),
    TA("ta"),
    OBSERVER("observer");

    private String name;

    public static ImportRoleEnum nameOf(String name) {
        for (ImportRoleEnum roleEnum : values()) {
            if (Objects.equals(roleEnum.name, name)) {
                return roleEnum;
            }
        }
        return null;
    }
}

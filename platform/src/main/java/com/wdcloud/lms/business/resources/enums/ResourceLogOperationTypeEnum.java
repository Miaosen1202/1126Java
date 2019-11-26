package com.wdcloud.lms.business.resources.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceLogOperationTypeEnum {

    Add("add"),

    EDIT("edit"),

    DELETE("delete"),

    UPDATE("update");

    private String type;

    public static ResourceLogOperationTypeEnum typeOf(String type) {
        for (ResourceLogOperationTypeEnum value : values()) {
            if (type.equals(value.type)) {
                return value;
            }
        }
        return null;
    }
}

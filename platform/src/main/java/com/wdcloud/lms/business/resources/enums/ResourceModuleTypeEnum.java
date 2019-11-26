package com.wdcloud.lms.business.resources.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResourceModuleTypeEnum {

    UPDATE("update"),

    IMPORT("import");

    private String type;

    public static ResourceModuleTypeEnum typeOf(String type) {
        for (ResourceModuleTypeEnum value : values()) {
            if (type.equals(value.type)) {
                return value;
            }
        }
        return null;
    }
}

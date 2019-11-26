package com.wdcloud.lms.business.sis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {
    ACTIVE("active"),
    INACTIVE("inactive"),
    DELETED("deleted"),
    COMPLETED("completed"),
    ACCEPTED("accepted");

    private String type;

    public static OperationTypeEnum typeOf(String type) {
        for (OperationTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

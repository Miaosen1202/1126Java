package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum OrgTypeEnum {
    SCHOOL(1),
    OTHER(2);

    private Integer type;

    public static OrgTypeEnum typeOf(Integer type) {
        for (OrgTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }

        return null;
    }
}

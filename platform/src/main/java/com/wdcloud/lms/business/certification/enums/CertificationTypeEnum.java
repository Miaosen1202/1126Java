package com.wdcloud.lms.business.certification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 认证类型 0：永久 1: 周期
 */
@Getter
@AllArgsConstructor
public enum CertificationTypeEnum {
    PERMANENT(0),
    CYCLE(1);

    private Integer value;

    public static CertificationTypeEnum valueOf(Integer type) {
        for (CertificationTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

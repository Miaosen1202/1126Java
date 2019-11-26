package com.wdcloud.lms.business.certification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 发行机构 0：Internal 1：External
 */
@Getter
@AllArgsConstructor
public enum CertificationIssuerEnum {
    INTERNAL(0),
    EXTERNAL(1);

    private Integer value;

    public static CertificationIssuerEnum valueOf(Integer type) {
        for (CertificationIssuerEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

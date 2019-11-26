package com.wdcloud.lms.business.certification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 操作类型 1:Mark Complete通过 2：Reject 拒绝3：Unenroll注销 4：ReEnroll重新注册
 */
@Getter
@AllArgsConstructor
public enum CertificationUserOpTypeEnum {
    MARKCOMPLETE (1),
    REJECT(2),
    UNENROLL(3),
    REENROLL(4);

    private Integer value;

    public static CertificationUserOpTypeEnum valueOf(Integer type) {
        for (CertificationUserOpTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

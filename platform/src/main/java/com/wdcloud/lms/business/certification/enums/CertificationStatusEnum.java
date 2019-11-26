package com.wdcloud.lms.business.certification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户证书状态：0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled
 */
@Getter
@AllArgsConstructor
public enum CertificationStatusEnum {
    ASSIGNED(0),
    PENDINGAPPROVAL(1),
    CERTIFIED(2),
    EXPIRED(3),
    UNENROLLED(4);

    private Integer value;

    public static CertificationStatusEnum valueOf(Integer type) {
        for (CertificationStatusEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

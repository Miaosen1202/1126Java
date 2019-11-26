package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum FilePermissionStatusEnum {
    /**
     * 发布
     */
    PUBLIC(1),

    /**
     * 未发布
     */
    UNPUBLIC(2),

    /**
     * 限制访问
     */
    RESTRICTED(3);

    private Integer status;

    public static FilePermissionStatusEnum statusOf(Integer status) {
        for (FilePermissionStatusEnum StatusEnum : values()) {
            if (Objects.equals(StatusEnum.status, status)) {
                return StatusEnum;
            }
        }

        return null;
    }
}

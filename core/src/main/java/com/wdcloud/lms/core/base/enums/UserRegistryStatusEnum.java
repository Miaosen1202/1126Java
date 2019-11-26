package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRegistryStatusEnum {
    /**
     * 邀请中
     */
    PENDING(1),

    /**
     * 已加入
     */
    JOINED(2);

    private Integer status;
}

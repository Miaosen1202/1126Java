package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserEPortfolioVisibilityTypeEnum {

    /**
     * 公开
     */
    PUBLIC(1),

    /**
     * 仅自己可见
     */
    ONLY_ME(2),

    /**
     * 朋友可见
     */
    FRIEND(3);

    private Integer type;

    public static UserEPortfolioVisibilityTypeEnum typeOf(Integer type) {
        for (UserEPortfolioVisibilityTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

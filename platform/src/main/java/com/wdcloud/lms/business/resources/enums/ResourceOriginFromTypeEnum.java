package com.wdcloud.lms.business.resources.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ResourceOriginFromTypeEnum {

    /**
     * 导入
     */
    IMPORT(1),

    /**
     * 分享
     */
    SHARE(2),

    /**
     * 喜爱
     */
    FAVORITE(3);

    private Integer type;

    public static ResourceOriginFromTypeEnum typeOf(Integer type) {
        for (ResourceOriginFromTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

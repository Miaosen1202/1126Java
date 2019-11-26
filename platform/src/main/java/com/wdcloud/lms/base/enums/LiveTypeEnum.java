package com.wdcloud.lms.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 直播第三方类型
 */
@AllArgsConstructor
@Getter
public enum LiveTypeEnum {
    /**
     * 展视互动
     */
    GENSEE("gensee");


    private String liveType;

    public static LiveTypeEnum liveTypeOf(String liveType) {
        for (LiveTypeEnum liveTypeEnm : values()) {
            if (Objects.equals(liveTypeEnm.liveType, liveType)) {
                return liveTypeEnm;
            }
        }
        return null;
    }
}

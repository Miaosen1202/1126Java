package com.wdcloud.lms.business.resources.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ThumbnailOriginTypeEnum {

    UPLOAD(1);

    private Integer type;

    public static ThumbnailOriginTypeEnum typeOf(Integer type) {
        for (ThumbnailOriginTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}

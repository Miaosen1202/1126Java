package com.wdcloud.lms.business.sis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SisCourseFormatEnum {
    ONLINE("online"),
    ON_CAMPUS("on_campus"),
    BLENDED("blended");

    private String format;

    public static SisCourseFormatEnum formatOf(String format) {
        for (SisCourseFormatEnum formatEnum : values()) {
            if (Objects.equals(formatEnum.format, format)) {
                return formatEnum;
            }
        }

        return null;
    }
}

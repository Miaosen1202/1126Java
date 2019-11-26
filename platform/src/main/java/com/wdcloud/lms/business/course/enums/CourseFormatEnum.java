package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程格式
 */
@Getter
@AllArgsConstructor
public enum CourseFormatEnum {
    /**
     * 未设置
     */
    UNSET(0),

    /**
     * 校内
     */
    ON_CAMPUS(1),

    /**
     * 在线
     */
    ONLINE(2),

    /**
     * 混合式
     */
    BLENDED(3);

    private Integer format;

    public static CourseFormatEnum formatOf(Integer format) {
        for (CourseFormatEnum formatEnum : values()) {
            if (Objects.equals(formatEnum.format, format)) {
                return formatEnum;
            }
        }

        return UNSET;
    }
}

package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程创建方式
 */
@Getter
@AllArgsConstructor
public enum CourseCreateModeEnum {
    /**
     * 导入
     */
    IMPORTED(1),

    /**
     * 教师创建
     */
    CREATED(2);

    private Integer mode;

    public static CourseCreateModeEnum modeOf(Integer mode) {
        for (CourseCreateModeEnum createModeEnum : values()) {
            if (Objects.equals(createModeEnum.mode, mode)) {
                return createModeEnum;
            }
        }
        return CREATED;
    }
}

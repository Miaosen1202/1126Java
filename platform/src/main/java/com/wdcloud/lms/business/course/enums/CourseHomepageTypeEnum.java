package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程首页
 */
@Getter
@AllArgsConstructor
public enum CourseHomepageTypeEnum {
    /**
     * 活动流
     */
    ACTIVE_STREAM("ACTIVE_STREAM"),

    /**
     * 单元
     */
    MODULE("MODULE"),

    /**
     * 作业组
     */
    ASSIGNMENTS("ASSIGNMENTS"),

    /**
     * 大纲
     */
    SYLLABUS("SYLLABUS"),

    /**
     * 页面
     */
    PAGE("PAGE");

    private String type;

    public static CourseHomepageTypeEnum typeOf(String type) {
        for (CourseHomepageTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }

        return ACTIVE_STREAM;
    }
}

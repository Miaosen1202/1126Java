package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程可见性
 */
@Getter
@AllArgsConstructor
public enum CourseVisibilityEnum {
    /**
     * 公开
     */
    PUBLIC(1),

    /**
     * 课程相关人员可见
     */
    COURSE(2),

    /**
     * 机构
     */
    ORGANIZATION(3);

    private Integer visibility;

    public static CourseVisibilityEnum visibilityOf(Integer visibility) {
        for (CourseVisibilityEnum visibilityEnum : values()) {
            if (Objects.equals(visibilityEnum.visibility, visibility)) {
                return visibilityEnum;
            }
        }

        return PUBLIC;
    }
}

package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程对于当前登录用户的状态
 */
@Getter
@AllArgsConstructor
public enum CourseStatusToUserEnum {

    /**
     * 已结课（涉及老师、学生）
     */
    CONCLUDED(0),

    /**
     * 冻结（涉及学生）
     */
    FROZEN(1),

    /**
     * 可使用状态（涉及老师、学生）
     */
    AVAILABLE(2);

    private Integer status;

    public static CourseStatusToUserEnum statusOf(Integer status) {
        for (CourseStatusToUserEnum statusToUserEnum : values()) {
            if (Objects.equals(statusToUserEnum.status, status)) {
                return statusToUserEnum;
            }
        }

        return null;
    }
}

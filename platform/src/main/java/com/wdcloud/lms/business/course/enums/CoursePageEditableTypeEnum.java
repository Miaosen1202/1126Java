package com.wdcloud.lms.business.course.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 课程Page可编辑的类型
 */
@Getter
@AllArgsConstructor
public enum CoursePageEditableTypeEnum {
    /**
     * 教师
     */
    TEACHER(1),

    /**
     * 教师与学生
     */
    TEACHER_STUDENT(2),

    /**
     * 所有人
     */
    ALL(3);

    private Integer type;

    public static CoursePageEditableTypeEnum typeOf(Integer type) {
        for (CoursePageEditableTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }

        return TEACHER;
    }
}

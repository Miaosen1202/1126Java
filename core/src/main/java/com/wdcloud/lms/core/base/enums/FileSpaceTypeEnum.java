package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 文件空间类型
 */
@Getter
@AllArgsConstructor
public enum FileSpaceTypeEnum {
    /**
     * 课程
     */
    COURSE(1),

    /**
     * 学习小组
     */
    STUDY_GROUP(2),

    /**
     * 个人
     */
    PERSONAL(3);

    private Integer type;

    public static FileSpaceTypeEnum typeOf(Integer type) {
        for (FileSpaceTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }

        return null;
    }
}

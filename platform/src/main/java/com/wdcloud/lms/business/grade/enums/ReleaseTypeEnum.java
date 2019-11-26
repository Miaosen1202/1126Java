package com.wdcloud.lms.business.grade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 个人或小组类型 1:个人 2小组：
 */
@Getter
@AllArgsConstructor
public enum  ReleaseTypeEnum {
    USER(1),
    GROUP(2);


    private Integer value;

    public static ReleaseTypeEnum valueOf(Integer type) {
        for (ReleaseTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

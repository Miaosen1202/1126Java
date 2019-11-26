package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 评分类型： 1：单一 2：批量
 */
@Getter
@AllArgsConstructor
public enum GradeTypeEnum {
    One(1),
    Batch(2);


    private Integer value;

    public static GradeTypeEnum valueOf(Integer type) {
        for (GradeTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
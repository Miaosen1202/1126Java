package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author  zhangxutao
 * 评分状态： 0 不评分  1 评分   2 全部
 */
@Getter
@AllArgsConstructor
public enum GradedStatusEnum {
    IsGrade(0),
    Grade(1),
    AllGrade(2);


    private Integer value;

    public static GradedStatusEnum valueOf(Integer type) {
        for (GradedStatusEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

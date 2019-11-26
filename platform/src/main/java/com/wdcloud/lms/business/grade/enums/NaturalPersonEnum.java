package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 是否小组中的自然人 0：不是 1 是自然人
 */
@Getter
@AllArgsConstructor
public enum NaturalPersonEnum {
    No(0),
    Yes(1);


    private Integer value;

    public static NaturalPersonEnum valueOf(Integer type) {
        for (NaturalPersonEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

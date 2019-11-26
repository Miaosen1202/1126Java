package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 用户类型：0 小组  1 个人【自然人】 2 ALL
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    Yes(0),
    No(1),
    All(2);


    private Integer value;

    public static UserTypeEnum valueOf(Integer type) {
        for (UserTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

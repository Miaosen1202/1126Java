package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 设置最低评分 0不设置  1设置
 */
@Getter
@AllArgsConstructor
public enum  SetLowestScoreEnum {
    NoSet(0),
    Set(1);


    private Integer value;

    public static SetLowestScoreEnum valueOf(Integer type) {
        for (SetLowestScoreEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author wangff
 * 得分显示类型 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
 */
@Getter
@AllArgsConstructor
public enum ShowScoreTypeEnum {
    PERCENTAGE(1),
    DIGIT(2),
    COMPLETION(3),
    LETTER(4),
    NOSCORE(5)
    ;
    private Integer value;

    public static ShowScoreTypeEnum valueOf(Integer type) {
        for (ShowScoreTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

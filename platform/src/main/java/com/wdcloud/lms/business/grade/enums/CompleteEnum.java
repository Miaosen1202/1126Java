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
public enum CompleteEnum {
    COMPLETE("Complete"),
    INCOMPLETE("Incomplete")
    ;
    private String value;

    public static CompleteEnum valueOfs(String type) {
        for (CompleteEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

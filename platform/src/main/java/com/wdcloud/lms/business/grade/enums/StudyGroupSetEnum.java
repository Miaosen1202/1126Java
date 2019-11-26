package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 是否小组（特殊处理）0：是小组、1不是小组、2小组覆盖
 */
@Getter
@AllArgsConstructor
public enum  StudyGroupSetEnum {
    Yes(0),
    No(1),
    GroupOverWrite(2);


    private Integer value;

    public static StudyGroupSetEnum valueOf(Integer type) {
        for (StudyGroupSetEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

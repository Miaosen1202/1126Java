package com.wdcloud.lms.business.grade.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhangxutao
 * 评分类型： 0:不是逾期提交 1:逾期提交
 */
@Getter
@AllArgsConstructor
public enum GradeOverdueEnum {
    NoOverdue(0),
    Overdue(1);


    private Integer value;

    public static GradeOverdueEnum valueOf(Integer type) {
        for (GradeOverdueEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.value, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

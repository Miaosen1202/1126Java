package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 问题类型枚举
 */
@Getter
@AllArgsConstructor
public enum QuestionTypeEnums {

    /**
     * 单选
     */
    CHOICE(1),

    /**
     * 多选
     */
    MULTI_CHOICE(2),

    /**
     * 判断题
     */
    TRUE_FALSE(3),

    /**
     * 多项下拉题
     */
    MULTI_SELECT(4),

    /**
     * 匹配题
     */
    MATCH(5),

    /**
     * 简答题
     */
    FULL_BLANK(6),

    /**
     * 文件上传题
     */
    FILE(7),

    /**
     * 文本题
     */
    TEXT(8),
    ;

    private Integer type;
}

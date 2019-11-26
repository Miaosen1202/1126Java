package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 问题类型
 *
 * @author 黄建林
 */
@Getter
@AllArgsConstructor
public enum QuestionTypeEnum {

    /**
     * 单选
     */
    MULTIPLE_CHOICE(1),

    /**
     * 多选
     */
    MULTIPLE_ANSWERS(2),

    /**
     * 判断题
     */
    TRUE_FALSE(3),
    /**
     * 多项下拉题
     */
    MULTIPLE_DROPDOWNS(4),

    /**
     * 匹配题
     */
    MATCHING(5),


    /**
     * 简答题
     */
    ESSAY_QUESTION(6),

    /**
     * 文件上传题
     */
    FILE_UPLOAD_QUESTION(7),

    /**
     * 文本题
     */
    TEXT(8);




    private Integer type;

    public static QuestionTypeEnum typeOf(Integer type) {
        for (QuestionTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }

        return TEXT;
    }
}

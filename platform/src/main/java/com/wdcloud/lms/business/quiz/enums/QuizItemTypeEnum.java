package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 测验问题类型
 *
 * @author 黄建林
 */
@Getter
@AllArgsConstructor
public enum QuizItemTypeEnum {

    /**
     * 只有问题，未分组
     */
    QUESTION(1),

    /**
     * 问题组
     */
    QUESTION_GROUP(2);

    private Integer type;

    public static QuizItemTypeEnum typeOf(Integer type) {
        for (QuizItemTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }

        return QUESTION_GROUP;
    }
}

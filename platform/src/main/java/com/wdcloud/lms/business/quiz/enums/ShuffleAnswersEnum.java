package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 答案重排
 */
@Getter
@AllArgsConstructor
public enum ShuffleAnswersEnum {
    /**
     * 答案重排
     */
    IS_SHUFFLE_ANSWER(1),
    /**
     * 答案不重排
     */
    IS_NOT_SHUFFLE_ANSWER(0);
    private Integer type;

    public static ShuffleAnswersEnum typeOf(Integer type) {
        for (ShuffleAnswersEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }

        return IS_NOT_SHUFFLE_ANSWER;
    }
}

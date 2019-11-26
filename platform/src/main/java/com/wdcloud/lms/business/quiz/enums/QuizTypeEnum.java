package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 测验类型
 */
@Getter
@AllArgsConstructor
public enum QuizTypeEnum {

    /**
     * 练习测验（不评分）
     */
    PRACTICE_QUIZ(1),

    /**
     * 评分测验（评分，关联到作业组）
     */
    GRADED_QUIZ(2),

    /**
     * 评分调查（评分，关联到作业组，提交后直接获得满分）
     */
    GRADED_SURVEY(3),

    /**
     * 不评分调查（不评分）
     */
    UNGRADED_SURVEY(4);

    private Integer type;

    public static QuizTypeEnum typeOf(Integer type) {
        for (QuizTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }

        return GRADED_QUIZ;
    }
}

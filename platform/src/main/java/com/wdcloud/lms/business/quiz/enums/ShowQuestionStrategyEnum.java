package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 答题时显示问题策略
 */
@Getter
@AllArgsConstructor
public enum ShowQuestionStrategyEnum {
    /**
     * 全部显示
     */
    ALL(0),

    /**
     * 每次一个
     */
    ONE_AT_A_TIME(1);

    private Integer strategy;

    public static ShowQuestionStrategyEnum strategyOf(Integer strategy) {
        for (ShowQuestionStrategyEnum strategyEnum : values()) {
            if (Objects.equals(strategyEnum.strategy, strategy)) {
                return strategyEnum;
            }
        }
        return ALL;
    }
}

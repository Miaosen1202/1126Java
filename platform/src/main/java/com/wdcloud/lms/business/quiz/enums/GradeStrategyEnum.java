package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 评分规则
 */
@Getter
@AllArgsConstructor
public enum GradeStrategyEnum {
    /**
     * 最高分
     */
    HIGHEST(1),

    /**
     * 最近提交
     */
    LATEST(2),

    /**
     * 平均分
     */
    AVERAGE(3);

    private Integer strategy;

    public static GradeStrategyEnum strategyOf(Integer strategy) {
        for (GradeStrategyEnum strategyEnum : values()) {
            if (Objects.equals(strategyEnum.strategy, strategy)) {
                return strategyEnum;
            }
        }
        return HIGHEST;
    }
}

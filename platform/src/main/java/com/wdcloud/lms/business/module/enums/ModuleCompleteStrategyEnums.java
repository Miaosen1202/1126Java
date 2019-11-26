package com.wdcloud.lms.business.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 完成策略
 */
@Getter
@AllArgsConstructor
public enum ModuleCompleteStrategyEnums {
    /**
     * 完成所有
     */
    ALL(1),
    /**
     * 按顺序完成所有
     */
    ALL_BY_ORDER(2),
    /**
     * 任意一个
     */
    ANYONE(3);

    private Integer strategy;

    public static ModuleCompleteStrategyEnums strategyOf(Integer strategy) {
        for (ModuleCompleteStrategyEnums strategyEnums : values()) {
            if (Objects.equals(strategyEnums.strategy, strategy)) {
                return strategyEnums;
            }
        }

        return null;
    }
}

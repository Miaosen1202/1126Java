package com.wdcloud.lms.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 移动策略
 */
@AllArgsConstructor
@Getter
public enum MoveStrategyEnum {
    /**
     * 默认
     */
    DEFAULT(0),
    /**
     * 置顶
     */
    TOP(1),
    /**
     * 置底
     */
    BOTTOM(2),
    /**
     * 指定xx之前
     */
    BEFORE(3),
    /**
     * 指定xx之后
     */
    AFTER(4);


    private int strategy;

    public static MoveStrategyEnum strategyOf(Integer strategy) {
        for (MoveStrategyEnum strategyEnums : values()) {
            if (Objects.equals(strategyEnums.strategy, strategy)) {
                return strategyEnums;
            }
        }
        return null;
    }
}

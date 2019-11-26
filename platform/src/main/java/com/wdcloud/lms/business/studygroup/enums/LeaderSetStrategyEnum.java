package com.wdcloud.lms.business.studygroup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 学习小组组长分配策略
 */
@Getter
@AllArgsConstructor
public enum LeaderSetStrategyEnum {
    /**
     * 手动指定
     */
    MANUAL(1),

    /**
     * 第一个加入
     */
    FIRST_JOIN(2),

    /**
     * 随机
     */
    RANDOM(3);

    private Integer strategy;

    public static LeaderSetStrategyEnum strategyOf(Integer strategy) {
        for (LeaderSetStrategyEnum value : values()) {
            if (Objects.equals(value.strategy, strategy)) {
                return value;
            }
        }
        return null;
    }
}

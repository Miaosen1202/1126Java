package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 显示学生的回答是否正确策略
 */
@Getter
@AllArgsConstructor
public enum ShowReplyStrategyEnum {
    /**
     * 不显示
     */
    NONE(0),

    /**
     * 每次提交
     */
    EACH_SUBMIT(1),

    /**
     * 最后一次提交
     */
    LAST_SUBMIT(2);

    private Integer strategy;

    public static ShowReplyStrategyEnum strategyOf(Integer strategy) {
        for (ShowReplyStrategyEnum strategyEnum : values()) {
            if (Objects.equals(strategyEnum.strategy, strategy)) {
                return strategyEnum;
            }
        }
        return NONE;
    }
}

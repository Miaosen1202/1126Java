package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 文件访问策略
 */
@Getter
@AllArgsConstructor
public enum FileAccessStrategyEnum {
    /**
     * 无，status为public时,或为restricted时设置
     */
    NONE(1),

    /**
     * 只能通过链接访问，status为restricted时设置
     */
    LINK(2),

    /**
     * 限制时间内访问，status为restricted时设置
     */
    SCHEDULE(3),

    /**
     * 禁止访问，status为unpublic时
     */
    FORBID(4);


    private Integer strategy;

    public static FileAccessStrategyEnum strategyOf(Integer strategy) {
        for (FileAccessStrategyEnum strategyEnum : values()) {
            if (Objects.equals(strategyEnum.strategy, strategy)) {
                return strategyEnum;
            }
        }
        return null;
    }
}

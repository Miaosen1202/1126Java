package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;

public abstract class AbstractAfterMoveStrategy {

    public MoveStrategyEnum support() {
        return MoveStrategyEnum.AFTER;
    }
}

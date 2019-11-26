package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.Strategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;

import java.util.Collection;

public interface DeleteStrategy extends Strategy {
    Integer delete(Long id);

    Integer delete(Collection<Long> ids);

    OriginTypeEnum support();
}

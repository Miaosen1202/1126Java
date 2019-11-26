package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.core.base.dao.PageDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractPageStrategy {
    @Autowired
    public PageDao pageDao;

    public OriginTypeEnum support() {
        return OriginTypeEnum.PAGE;
    }
}

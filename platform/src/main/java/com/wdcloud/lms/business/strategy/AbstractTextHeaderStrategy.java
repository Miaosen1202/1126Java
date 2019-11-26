package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.dao.TextHeaderDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WangYaRong
 */
public abstract class AbstractTextHeaderStrategy {

    @Autowired
    public AssignDao assignDao;
    @Autowired
    public TextHeaderDao textHeaderDao;

    public OriginTypeEnum support() {
        return OriginTypeEnum.TEXT_HEADER;
    }
}

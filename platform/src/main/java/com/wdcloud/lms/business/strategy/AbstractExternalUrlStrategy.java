package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.dao.ExternalUrlDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WangYaRong
 */
public abstract class AbstractExternalUrlStrategy {

    @Autowired
    public AssignDao assignDao;
    @Autowired
    public ExternalUrlDao externalUrlDao;

    public OriginTypeEnum support() {
        return OriginTypeEnum.EXTERNAL_URL;
    }
}

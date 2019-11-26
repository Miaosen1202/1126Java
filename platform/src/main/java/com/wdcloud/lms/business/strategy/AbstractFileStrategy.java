package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.UserFileDataEdit;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFileStrategy {
    @Autowired
    public UserFileDao userFileDao;
    @Autowired
    public UserFileDataEdit userFileDataEdit;
    @Autowired
    public UserFileService userFileService;

    public OriginTypeEnum support() {
        return OriginTypeEnum.FILE;
    }
}

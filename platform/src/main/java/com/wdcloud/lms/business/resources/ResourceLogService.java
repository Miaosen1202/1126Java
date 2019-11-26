package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.core.base.dao.ResourceImportDao;
import com.wdcloud.lms.core.base.dao.ResourceImportLogDao;
import com.wdcloud.lms.core.base.dao.ResourceUpdateLogDao;
import com.wdcloud.lms.core.base.model.ResourceImportedLog;
import com.wdcloud.lms.core.base.model.ResourceUpdateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResourceLogService {

    @Autowired
    private ResourceImportLogDao resourceImportLogDao;
    @Autowired
    private ResourceUpdateLogDao resourceUpdateLogDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(ResourceUpdateLog resourceUpdateLog) {
        resourceUpdateLogDao.save(resourceUpdateLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(ResourceImportedLog resourceImportedLog){
        resourceImportLogDao.save(resourceImportedLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(ResourceUpdateLog resourceUpdateLog){
        resourceUpdateLogDao.update(resourceUpdateLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(ResourceImportedLog resourceImportedLog){
        resourceImportLogDao.update(resourceImportedLog);
    }
}

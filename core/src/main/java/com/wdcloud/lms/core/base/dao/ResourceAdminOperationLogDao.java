package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceAdminOperationLogExtMapper;
import com.wdcloud.lms.core.base.model.ResourceAdminOperationLog;
import com.wdcloud.lms.core.base.vo.resource.ResourceAdminOperationLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceAdminOperationLogDao extends CommonDao<ResourceAdminOperationLog, Long> {

    @Autowired
    private ResourceAdminOperationLogExtMapper resourceAdminOperationLogExtMapper;

    @Override
    protected Class<ResourceAdminOperationLog> getBeanClass() {
        return ResourceAdminOperationLog.class;
    }

    public List<ResourceAdminOperationLogVO> getByResourceAuthorId(Long authorId){
        return resourceAdminOperationLogExtMapper.getByResourceAuthorId(authorId);
    }

    public int countByIsSeeAndResourceAuthorId(Integer isSee, Long authorId){
        return resourceAdminOperationLogExtMapper.countByIsSeeAndResourceAuthorId(isSee, authorId);
    }

}

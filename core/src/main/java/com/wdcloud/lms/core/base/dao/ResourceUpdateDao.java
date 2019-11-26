package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceExtMapper;
import com.wdcloud.lms.core.base.mapper.ext.ResourceUpdateExtMapper;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceUpdateDao extends CommonDao<ResourceUpdate, Long> {

    @Autowired
    private ResourceUpdateExtMapper resourceUpdateExtMapper;

    @Override
    protected Class<ResourceUpdate> getBeanClass() {
        return ResourceUpdate.class;
    }

    public ResourceUpdate getByResourceId(Long resourceId){
        ResourceUpdate resourceUpdate = ResourceUpdate.builder().resourceId(resourceId).build();
        return this.findOne(resourceUpdate);
    }

    public Long getVersionIdByResourceId(Long resourceId) {
        ResourceUpdate resourceUpdate = this.getByResourceId(resourceId);
        return resourceUpdate.getVersionId();
    }

    public ResourceVO getEditDataByResourceId(Long resourceId) {
        return resourceUpdateExtMapper.getEditDataByResourceId(resourceId);
    }
}

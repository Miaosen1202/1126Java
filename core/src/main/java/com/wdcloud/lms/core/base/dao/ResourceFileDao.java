package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.ResourceFile;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceFileDao extends CommonDao<ResourceFile, Long > {

    @Override
    protected Class<ResourceFile> getBeanClass() {
        return ResourceFile.class;
    }

    public String getFileUrlByCondition(Long resourceId, Integer type, String fileType){
        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId)
                .type(type).fileType(fileType).build();
        return this.findOne(resourceFile).getFileUrl();
    }

    public ResourceFile getByCondition(Long resourceId, Integer type, String fileType){
        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId)
                .type(type).fileType(fileType).build();
        return this.findOne(resourceFile);
    }

    public ResourceFile getByCondition(Long resourceId, Integer type) {
        ResourceFile resourceFile = ResourceFile.builder().resourceId(resourceId)
                .type(type).build();
        return this.findOne(resourceFile);
    }
}

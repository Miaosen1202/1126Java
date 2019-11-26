package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceTagExtMapper;
import com.wdcloud.lms.core.base.model.ResourceTag;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceTagDao extends CommonDao<ResourceTag, Long> {

    @Autowired
    private ResourceTagExtMapper resourceTagExtMapper;

    @Override
    protected Class<ResourceTag> getBeanClass() {
        return ResourceTag.class;
    }

    public void batchInsert(List<ResourceTag> resourceTags) {
        if (ListUtils.isNotEmpty(resourceTags)) {
            resourceTagExtMapper.batchInsert(resourceTags);
        }
    }
}


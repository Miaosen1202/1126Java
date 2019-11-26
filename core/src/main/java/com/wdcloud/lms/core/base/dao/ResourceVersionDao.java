package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceVersionExtMapper;
import com.wdcloud.lms.core.base.model.ResourceVersion;
import com.wdcloud.lms.core.base.vo.resource.ResourceVersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class ResourceVersionDao extends CommonDao<ResourceVersion, Long> {

    @Autowired
    private ResourceVersionExtMapper resourceVersionExtMapper;

    @Override
    protected Class<ResourceVersion> getBeanClass() {
        return ResourceVersion.class;
    }

    public ResourceVersion getLatestByResourceId(Long resourceId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(ResourceVersion.RESOURCE_ID, resourceId);
        example.orderBy(ResourceVersion.VERSION).desc();
        return this.findOne(example);
    }

    public List<ResourceVersionVO> getByResourceId(Long resourceId) {
        return resourceVersionExtMapper.getByResourceId(resourceId);
    }
}

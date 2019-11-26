package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.ResourceImportGeneration;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Repository
public class ResourceImportGenerationDao extends CommonDao<ResourceImportGeneration, Long> {
    @Override
    protected Class<ResourceImportGeneration> getBeanClass() {
        return ResourceImportGeneration.class;
    }

    public ResourceImportGeneration getByImportIdAndOriginIdAndType(Long importId, Long originId, Integer type) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(ResourceImportGeneration.RESOURCE_IMPORT_ID, importId)
                .andEqualTo(ResourceImportGeneration.ORIGIN_ID, originId)
                .andEqualTo(ResourceImportGeneration.ORIGIN_TYPE, type);
        example.setOrderByClause(" id desc ");

        return this.findOne(example);
    }

    public ResourceImportGeneration getByImportIdAndNewIdAndType(Long importId, Long newId, Integer type) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(ResourceImportGeneration.RESOURCE_IMPORT_ID, importId)
                .andEqualTo(ResourceImportGeneration.NEW_ID, newId)
                .andEqualTo(ResourceImportGeneration.ORIGIN_TYPE, type);
        example.setOrderByClause(" id desc ");

        return this.findOne(example);
    }
}

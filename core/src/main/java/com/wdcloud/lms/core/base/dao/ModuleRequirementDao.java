package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModuleRequirementExtMapper;
import com.wdcloud.lms.core.base.model.ModuleRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ModuleRequirementDao extends CommonDao<ModuleRequirement, Long> {
    @Autowired
    private ModuleRequirementExtMapper moduleRequirementExtMapper;

    public List<ModuleRequirement> getModuleRequirements(Map<String, Object> params) {
        return moduleRequirementExtMapper.getModuleRequirements(params);
    }
    @Override
    protected Class<ModuleRequirement> getBeanClass() {
        return ModuleRequirement.class;
    }
}
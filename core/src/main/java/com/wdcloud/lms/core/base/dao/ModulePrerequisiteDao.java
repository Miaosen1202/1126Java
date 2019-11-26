package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModulePrerequisiteExtMapper;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModulePrerequisite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ModulePrerequisiteDao extends CommonDao<ModulePrerequisite, Long> {

    @Autowired
    private ModulePrerequisiteExtMapper modulePrerequisiteExtMapper;

    public List<Module> getModulePrerequisites(Map<String, Object> params) {
        return modulePrerequisiteExtMapper.getModulePrerequisites(params);
    }
    @Override
    protected Class<ModulePrerequisite> getBeanClass() {
        return ModulePrerequisite.class;
    }
}
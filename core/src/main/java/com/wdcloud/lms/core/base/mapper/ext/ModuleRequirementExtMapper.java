package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ModuleRequirement;

import java.util.List;
import java.util.Map;

public interface ModuleRequirementExtMapper {
    List<ModuleRequirement> getModuleRequirements(Map<String, Object> param);
}
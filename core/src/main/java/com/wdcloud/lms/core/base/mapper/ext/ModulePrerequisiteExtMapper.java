package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Module;

import java.util.List;
import java.util.Map;

public interface ModulePrerequisiteExtMapper {
    List<Module> getModulePrerequisites(Map<String, Object> param);
}
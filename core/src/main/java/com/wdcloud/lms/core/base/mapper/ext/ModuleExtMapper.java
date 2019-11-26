package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.vo.ModuleVO;

import java.util.List;
import java.util.Map;

public interface ModuleExtMapper {
    List<ModuleVO> getModuleWithinItemsCount(Map<String, Object> param);

    Integer getMaxSeq(Long courseId);

    List<Module> getModuleByOriginTypeAndOriginId(Map<String, Object> params);

    List<ModuleVO> getModuleCompleteStatusByModuleAndUser(Map<String, Object> params);
}
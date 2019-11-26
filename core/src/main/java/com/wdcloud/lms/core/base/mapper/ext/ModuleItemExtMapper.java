package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.lms.core.base.vo.ModuleItemCompleteVO;

import java.util.List;
import java.util.Map;

public interface ModuleItemExtMapper {
 
    Integer getMaxSeq(Long moduleId);
    Integer getMinSeq(Long moduleId);

    Integer updateBatchSeqAndModuleId(List<ModuleItem> moduleItems);

    List<ModuleItem> getModuleItemByCourse(Long courseId);

    List<ModuleItemCompleteVO> getModuleItemCompleteStatusByModuleAndUser(Map<String, Object> params);
}
package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.vo.AssignmentVO;
import com.wdcloud.lms.core.base.vo.ExternalUrlDetailVo;

import java.util.List;
import java.util.Map;

public interface ExternalUrlExtMapper {
    public List<ExternalUrlDetailVo> findExternalUrlByModuleItem(Long moduleItemId);
}

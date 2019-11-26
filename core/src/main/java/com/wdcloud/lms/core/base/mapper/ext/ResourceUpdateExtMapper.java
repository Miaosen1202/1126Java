package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceVO;

public interface ResourceUpdateExtMapper {

    ResourceVO getEditDataByResourceId(Long resourceId);
}

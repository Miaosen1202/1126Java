package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceVersionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceVersionExtMapper {
    List<ResourceVersionVO> getByResourceId(@Param("resourceId") long resourceId);
}

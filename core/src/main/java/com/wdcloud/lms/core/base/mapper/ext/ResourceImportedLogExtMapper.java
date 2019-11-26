package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceImportLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceImportedLogExtMapper {

    List<ResourceImportLogVO> getByImportUserId(@Param("userId") long userId);
}

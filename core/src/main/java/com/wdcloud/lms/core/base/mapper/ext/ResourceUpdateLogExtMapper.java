package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceUpdateLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceUpdateLogExtMapper {
    List<ResourceUpdateLogVO> getByAuthorId(@Param("authorId") long authorId);

}

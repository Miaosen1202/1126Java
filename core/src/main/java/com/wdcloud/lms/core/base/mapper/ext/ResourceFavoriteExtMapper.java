package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceFavoriteExtMapper {
    List<ResourceVO> getByUserIdAndTypeAndShareRange(@Param("userId") long userId, @Param("types") List<String> types);
}

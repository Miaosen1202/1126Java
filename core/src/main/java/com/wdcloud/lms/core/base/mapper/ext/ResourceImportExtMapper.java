package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.resource.ResourceCourseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceImportExtMapper {

    List<ResourceCourseVO> getCourseByResourceIdAndUserId(@Param("resourceId") Long resourceId, @Param("userId") Long userId);

    List<ResourceCourseVO> getCourseByNotInResourceAndUserId(@Param("resourceId") Long resourceId, @Param("userId") Long userId);

    List<ResourceVO> getByUserIdAndTypeAndVersionMessageSort(@Param("userId")Long userId, @Param("types") List<String> types);
}

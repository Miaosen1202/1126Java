package com.wdcloud.lms.core.base.mapper.ext;


import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceExtMapper {

    ResourceVO getByIdAndImportUserId(@Param("id") Long id, @Param("userId") Long importUserId);

    List<ResourceVO> getByOriginIdAndOriginType(@Param("originId") long originId, @Param("originType") int originType);

    List<ResourceVO> getByAuthorIdAndType(@Param("authorId") long authorId, @Param("types") List<String> types);

    List<ResourceVO> getByShareRangeAndCondition(@Param("authorId") long authorId, @Param("types") List<String> types,
        @Param("grade") Integer grade, @Param("name") String name,
        @Param("rootOrgTreeId") String rootOrgTreeId, @Param("updateTime") String updateTime,
        @Param("importCount") String importCount);

    List<ResourceVO> getByAdminRoleAndCondition(@Param("types") List<String> types, @Param("name") String name,
                                                 @Param("rootOrgTreeId") String rootOrgTreeId, @Param("updateTime") String updateTime,
                                                 @Param("importCount") String importCount);
}

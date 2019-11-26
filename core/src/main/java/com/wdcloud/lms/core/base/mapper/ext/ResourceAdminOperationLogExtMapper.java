package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ResourceAdminOperationLog;
import com.wdcloud.lms.core.base.vo.resource.ResourceAdminOperationLogVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceAdminOperationLogExtMapper {

    List<ResourceAdminOperationLogVO> getByResourceAuthorId(@Param("authorId") long authorId);

    int countByIsSeeAndResourceAuthorId(@Param("isSee") int isSee, @Param("authorId") long authorId);
}
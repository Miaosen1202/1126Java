package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ResourceTag;

import java.util.List;

public interface ResourceTagExtMapper {

    /**
     * 批量插入资源标签
     * @param resourceTags
     */
    void batchInsert(List<ResourceTag> resourceTags);
}

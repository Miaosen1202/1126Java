package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.MessageRelated;

import java.util.List;

public interface MessageRelatedExtMapper {
    void batchSave(List<MessageRelated> messageRelatedList);
}

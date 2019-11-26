package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.MessageSubjectUserStar;

import java.util.List;

public interface MessageSubjectUserStarExtMapper {
    int batchSave(List<MessageSubjectUserStar> messageSubjectUserStarList);
}

package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserLink;

import java.util.List;

public interface UserLinkExtMapper {
    int batchInsert(List<UserLink> userLinkList);

    int batchUpdate(List<UserLink> userLinkList);

}

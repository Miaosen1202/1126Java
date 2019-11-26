package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceVersionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ResourceVersionMessageDao extends CommonDao<ResourceVersionMessage, Long> {

    @Override
    protected Class<ResourceVersionMessage> getBeanClass() {
        return ResourceVersionMessage.class;
    }

    public int countByCondition(Long userId, int isRemind) {
        ResourceVersionMessage resourceVersionMessage =  ResourceVersionMessage.builder().userId(userId)
                .isRemind(isRemind).build();
        return this.count(resourceVersionMessage);
    }

    public List<ResourceVersionMessage> findByResourceId(Long resourceId) {
        ResourceVersionMessage versionMessage = ResourceVersionMessage.builder().resourceId(resourceId).build();
        return this.find(versionMessage);
    }
}

package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.UserConfig;
import org.springframework.stereotype.Repository;

@Repository
public class UserConfigDao extends CommonDao<UserConfig, Long> {

    public UserConfig findByUserId(long userId) {
        return findOne(UserConfig.builder().userId(userId).build());
    }

    @Override
    protected Class<UserConfig> getBeanClass() {
        return UserConfig.class;
    }
}
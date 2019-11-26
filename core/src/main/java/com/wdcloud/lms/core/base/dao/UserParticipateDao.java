package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.UserParticipate;
import org.springframework.stereotype.Repository;

@Repository
public class UserParticipateDao extends CommonDao<UserParticipate, Long> {

    @Override
    protected Class<UserParticipate> getBeanClass() {
        return UserParticipate.class;
    }
}
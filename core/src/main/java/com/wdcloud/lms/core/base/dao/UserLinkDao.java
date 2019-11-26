package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserLinkExtMapper;
import com.wdcloud.lms.core.base.model.UserLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLinkDao extends CommonDao<UserLink, Long> {

    @Autowired
    private UserLinkExtMapper userLinkExtMapper;

    @Override
    protected Class<UserLink> getBeanClass() {
        return UserLink.class;
    }

    public int batchInsert(List<UserLink> userLinkList) {
        return userLinkExtMapper.batchInsert(userLinkList);
    }

    public int batchUpdate(List<UserLink> userLinkList) {
        return userLinkExtMapper.batchUpdate(userLinkList);
    }
}
package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.UserEmail;
import com.wdcloud.lms.core.base.mapper.ext.UserEmailExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserEmailDao extends CommonDao<UserEmail, Long> {


    @Autowired
    private UserEmailExtMapper userEmailExtMapper;

    @Override
    public Class<UserEmail> getBeanClass() {
        return UserEmail.class;
    }

    public int getEmailIsExist(String email) {
        return userEmailExtMapper.getEmailIsExist(email);
    }

}
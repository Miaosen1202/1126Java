package com.wdcloud.lms.core.base.dao;

import com.github.pagehelper.PageHelper;
import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.LoginRecord;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class LoginRecordDao extends CommonDao<LoginRecord, Long> {

    public LoginRecord getLastLoginRecord(Long userId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(LoginRecord.USER_ID, userId);
        example.setOrderByClause("create_time desc");

        PageHelper.startPage(1, 1);
        return findOne(example);
    }

    @Override
    protected Class<LoginRecord> getBeanClass() {
        return LoginRecord.class;
    }
}
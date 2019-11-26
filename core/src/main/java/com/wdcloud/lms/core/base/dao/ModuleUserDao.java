package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModuleUserExtMapper;
import com.wdcloud.lms.core.base.model.ModuleUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleUserDao extends CommonDao<ModuleUser, Long> {

    @Autowired
    private ModuleUserExtMapper moduleUserExtMapper;
    @Override
    protected Class<ModuleUser> getBeanClass() {
        return ModuleUser.class;
    }

    public void batchInsert(List<ModuleUser> moduleUserList) {
        if (ListUtils.isNotEmpty(moduleUserList)) {
            moduleUserExtMapper.batchInsert(moduleUserList);
        }
    }

    public void batchUpdate(List<ModuleUser> moduleUserList) {
        if (ListUtils.isNotEmpty(moduleUserList)) {
            moduleUserExtMapper.batchUpdate(moduleUserList);
        }
    }

    public void batchDelete(List<ModuleUser> moduleUserList) {
        if (ListUtils.isNotEmpty(moduleUserList)) {
            moduleUserExtMapper.batchDelete(moduleUserList);
        }
    }

}
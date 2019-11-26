package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModuleItemUserExtMapper;
import com.wdcloud.lms.core.base.model.ModuleItemUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ModuleItemUserDao extends CommonDao<ModuleItemUser, Long> {

    @Autowired
    private ModuleItemUserExtMapper moduleItemUserExtMapper;

    @Override
    protected Class<ModuleItemUser> getBeanClass() {
        return ModuleItemUser.class;
    }

    public void batchInsert(List<ModuleItemUser> moduleItemUserList) {
        if (ListUtils.isNotEmpty(moduleItemUserList)) {
            moduleItemUserExtMapper.batchInsert(moduleItemUserList);
        }
    }

    public void batchUpdate(List<ModuleItemUser> moduleItemUserList) {
        if (ListUtils.isNotEmpty(moduleItemUserList)) {
            moduleItemUserExtMapper.batchUpdate(moduleItemUserList);
        }
    }

    public void batchDelete(List<ModuleItemUser> moduleItemUserList) {
        if (ListUtils.isNotEmpty(moduleItemUserList)) {
            moduleItemUserExtMapper.batchDelete(moduleItemUserList);
        }
    }

    public int getModuleItemUserCountByModule(Map<String, Object> map) {
        return moduleItemUserExtMapper.getModuleItemUserCountByModule(map);
    }

    public int getInCompleteModuleItemUserCountByModule(Map<String, Object> map) {
        return moduleItemUserExtMapper.getInCompleteModuleItemUserCountByModule(map);
    }

}
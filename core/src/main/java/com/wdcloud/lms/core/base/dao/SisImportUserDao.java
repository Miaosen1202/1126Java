package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportUserExtMapper;
import com.wdcloud.lms.core.base.model.SisImportUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportUserDao extends CommonDao<SisImportUser, Long> {
    @Autowired
    private SisImportUserExtMapper sisImportUserExtMapper;

    public List<SisImportUser> findByUserIds(Collection<String> userIds, String treeId) {
        if (userIds == null || userIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportUser.USER_ID, userIds)
                .andLike(SisImportUser.ORG_TREE_ID, treeId + "%");
        return find(example);
    }

    @Override
    protected Class<SisImportUser> getBeanClass() {
        return SisImportUser.class;
    }

    public void batchSaveOrUpdate(List<SisImportUser> importUsers) {
        if (ListUtils.isNotEmpty(importUsers)) {
            sisImportUserExtMapper.batchSaveOrUpdate(importUsers);
        }
    }
}
package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportOrgExtMapper;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportOrgDao extends CommonDao<SisImportOrg, Long> {
    @Autowired
    private SisImportOrgExtMapper extMapper;

    public boolean existsAccountId(String accountId) {
        return count(SisImportOrg.builder().accountId(accountId).build()) > 0;
    }

    public void batchSave(List<SisImportOrg> orgs) {
        extMapper.batchSave(orgs);
    }

    public List<SisImportOrg> findByAccountIds(Collection<String> accountIds, String treeId) {
        if (accountIds == null || accountIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportOrg.ACCOUNT_ID, accountIds)
                .andLike(SisImportOrg.ORG_TREE_ID, treeId + "%");
        return find(example);
    }

    public void batchSaveOrUpdate(List<SisImportOrg> importOrgs) {
        if (ListUtils.isNotEmpty(importOrgs)) {
            extMapper.batchSaveOrUpdate(importOrgs);
        }
    }

    @Override
    protected Class<SisImportOrg> getBeanClass() {
        return SisImportOrg.class;
    }
}
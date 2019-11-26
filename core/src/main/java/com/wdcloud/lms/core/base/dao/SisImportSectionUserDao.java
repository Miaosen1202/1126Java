package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportSectionUserExtMapper;
import com.wdcloud.lms.core.base.model.SisImportSectionUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportSectionUserDao extends CommonDao<SisImportSectionUser, Long> {
    @Autowired
    private SisImportSectionUserExtMapper sisImportSectionUserExtMapper;

    @Override
    protected Class<SisImportSectionUser> getBeanClass() {
        return SisImportSectionUser.class;
    }

    public void batchSaveOrUpdate(List<SisImportSectionUser> sectionUsers) {
        if (ListUtils.isNotEmpty(sectionUsers)) {
            sisImportSectionUserExtMapper.batchSaveOrUpdate(sectionUsers);
        }
    }

    public List<SisImportSectionUser> findByUserIds(Collection<String> userIds, String treeId) {
        if (userIds == null || userIds.isEmpty()) {
            return new ArrayList<>();
        }

        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportSectionUser.USER_ID, userIds)
                .andLike(SisImportSectionUser.ORG_TREE_ID, treeId + "%");
        return find(example);
    }
}
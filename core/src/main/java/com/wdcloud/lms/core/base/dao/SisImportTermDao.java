package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportTermExtMapper;
import com.wdcloud.lms.core.base.model.SisImportTerm;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportTermDao extends CommonDao<SisImportTerm, Long> {
    @Autowired
    private SisImportTermExtMapper sisImportTermExtMapper;

    public List<SisImportTerm> findByTermIds(Collection<String> termIds, String rootOrgTreeId) {
        if (termIds == null || termIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportTerm.TERM_ID, termIds)
                .andLike(SisImportTerm.ORG_TREE_ID, rootOrgTreeId + "%");
        return find(example);
    }

    public void batchSaveOrUpdate(List<SisImportTerm> sisImportTerms) {
        if (ListUtils.isNotEmpty(sisImportTerms)) {
            sisImportTermExtMapper.batchSaveOrUpdate(sisImportTerms);
        }
    }

    @Override
    protected Class<SisImportTerm> getBeanClass() {
        return SisImportTerm.class;
    }
}
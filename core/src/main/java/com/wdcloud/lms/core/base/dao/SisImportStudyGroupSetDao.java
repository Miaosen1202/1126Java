package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportStudyGroupSetExtMapper;
import com.wdcloud.lms.core.base.model.SisImportStudyGroupSet;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportStudyGroupSetDao extends CommonDao<SisImportStudyGroupSet, Long> {
    @Autowired
    private SisImportStudyGroupSetExtMapper sisImportStudyGroupSetExtMapper;

    @Override
    protected Class<SisImportStudyGroupSet> getBeanClass() {
        return SisImportStudyGroupSet.class;
    }

    public List<SisImportStudyGroupSet> findByGroupCategoryIds(Collection<String> groupCategoryId, String orgTreeId) {
        if (groupCategoryId == null || groupCategoryId.isEmpty()) {
            return new ArrayList<>();
        }

        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportStudyGroupSet.GROUP_CATEGORY_ID, groupCategoryId)
                .andLike(SisImportStudyGroupSet.ORG_TREE_ID, orgTreeId + "%");
        return find(example);
    }

    public void batchSaveOrUpdate(List<SisImportStudyGroupSet> studyGroupSets) {
        if (ListUtils.isNotEmpty(studyGroupSets)) {
            sisImportStudyGroupSetExtMapper.batchSaveOrUpdate(studyGroupSets);
        }
    }
}
package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.SisImportStudyGroupMapper;
import com.wdcloud.lms.core.base.mapper.ext.SisImportStudyGroupExtMapper;
import com.wdcloud.lms.core.base.model.SisImportStudyGroup;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportStudyGroupDao extends CommonDao<SisImportStudyGroup, Long> {
    @Autowired
    private SisImportStudyGroupExtMapper sisImportStudyGroupExtMapper;

    @Override
    protected Class<SisImportStudyGroup> getBeanClass() {
        return SisImportStudyGroup.class;
    }

    public void batchSaveOrUpdate(List<SisImportStudyGroup> sisImportStudyGroups) {
        if (ListUtils.isNotEmpty(sisImportStudyGroups)) {
            sisImportStudyGroupExtMapper.batchSaveOrUpdate(sisImportStudyGroups);
        }
    }

    public List<SisImportStudyGroup> findByStudyGroupIds(Collection<String> studyGroupIds, String orgTreeId) {
        if (studyGroupIds == null || studyGroupIds.isEmpty()) {
            return new ArrayList<>();
        }

        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportStudyGroup.GROUP_ID, studyGroupIds)
                .andLike(SisImportStudyGroup.ORG_TREE_ID, orgTreeId + "%");

        return find(example);
    }
}
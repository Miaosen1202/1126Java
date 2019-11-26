package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportSectionExtMapper;
import com.wdcloud.lms.core.base.model.SisImportSection;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class SisImportSectionDao extends CommonDao<SisImportSection, Long> {
    @Autowired
    private SisImportSectionExtMapper sisImportSectionExtMapper;

    @Override
    protected Class<SisImportSection> getBeanClass() {
        return SisImportSection.class;
    }

    public List<SisImportSection> findBySectionIds(Collection<String> sectionIds, String treeId) {
        if (sectionIds == null || sectionIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(SisImportSection.SECTION_ID, sectionIds)
                .andLike(SisImportSection.ORG_TREE_ID, treeId + "%");
        return find(example);
    }

    public void batchSaveOrUpdate(List<SisImportSection> sections) {
        if (ListUtils.isNotEmpty(sections)) {
            sisImportSectionExtMapper.batchSaveOrUpdate(sections);
        }
    }
}
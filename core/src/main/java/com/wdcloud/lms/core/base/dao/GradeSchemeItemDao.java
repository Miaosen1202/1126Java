package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.GradeSchemeItemExtMapper;
import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GradeSchemeItemDao extends CommonDao<GradeSchemeItem, Long> {
    @Autowired
    private GradeSchemeItemExtMapper gradeSchemeItemExtMapper;

    @Override
    protected Class<GradeSchemeItem> getBeanClass() {
        return GradeSchemeItem.class;
    }

    public void batchInsert(List<GradeSchemeItem> schemeItems) {
        gradeSchemeItemExtMapper.batchInsert(schemeItems);
    }
}
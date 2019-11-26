package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.GradeScheme;
import org.springframework.stereotype.Repository;

@Repository
public class GradeSchemeDao extends CommonDao<GradeScheme, Long> {

    @Override
    protected Class<GradeScheme> getBeanClass() {
        return GradeScheme.class;
    }
}
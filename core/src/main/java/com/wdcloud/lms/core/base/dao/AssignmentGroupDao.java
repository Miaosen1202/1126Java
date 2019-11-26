package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentGroupExtMapper;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class AssignmentGroupDao extends CommonDao<AssignmentGroup, Long> {
    @Autowired
    private AssignmentGroupExtMapper assignmentGroupExtMapper;
    public AssignmentGroupExtMapper ext(){
        return assignmentGroupExtMapper;
    }
    @Override
    protected Class<AssignmentGroup> getBeanClass() {
        return AssignmentGroup.class;
    }
}
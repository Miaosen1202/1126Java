package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.business.assignment.AssignmentDataEdit;
import com.wdcloud.lms.business.assignment.AssignmentDataQuery;
import com.wdcloud.lms.business.strategy.add.AssignmentAdd;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAssignmentStrategy {
    @Autowired
    public AssignmentDao assignmentDao;
    @Autowired
    public AssignDao assignDao;
    @Autowired
    public AssignmentReplyDao assignmentReplyDao;
    @Autowired
    public AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    public ModuleItemDao moduleItemDao;
    @Autowired
    public UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    public AssignmentDataEdit assignmentDataEdit;
    @Autowired
    public AssignmentDataQuery assignmentDataQuery;
    @Autowired
    public ResourceCommonService resourceCommonService;
    @Autowired
    public AssignmentGroupItemChangeRecordDao assignmentGroupItemChangeRecordDao;
    @Autowired
    public ResourceImportGenerationDao resourceImportGenerationDao;
    @Autowired
    public AssignmentAdd assignmentAdd;

    public OriginTypeEnum support() {
        return OriginTypeEnum.ASSIGNMENT;
    }
}

package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.discussion.DiscussionDataEdit;
import com.wdcloud.lms.business.discussion.DiscussionDataQuery;
import com.wdcloud.lms.business.strategy.add.DiscussionAdd;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDiscussionStrategy {
    @Autowired
    public DiscussionDao discussionDao;
    @Autowired
    public DiscussionReplyDao discussionReplyDao;
    @Autowired
    public AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    public AssignDao assignDao;
    @Autowired
    public AssignUserDao assignUserDao;
    @Autowired
    public UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    public DiscussionDataEdit discussionDataEdit;
    @Autowired
    public DiscussionDataQuery discussionDataQuery;
    @Autowired
    public ModuleItemDao moduleItemDao;
    @Autowired
    public ResourceCommonService resourceCommonService;
    @Autowired
    public AssignmentGroupItemChangeRecordDao assignmentGroupItemChangeRecordDao;
    @Autowired
    public UserFileService userFileService;
    @Autowired
    public ResourceImportGenerationDao resourceImportGenerationDao;
    @Autowired
    public ResourceFileDao resourceFileDao;
    @Autowired
    public DiscussionAdd discussionAdd;
    @Autowired
    public AssignService assignService;

    public OriginTypeEnum support() {
        return OriginTypeEnum.DISCUSSION;
    }
}

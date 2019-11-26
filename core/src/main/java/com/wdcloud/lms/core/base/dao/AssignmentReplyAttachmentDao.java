package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.AssignmentReplyAttachment;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentReplyAttachmentDao extends CommonDao<AssignmentReplyAttachment, Long> {

    @Override
    public Class<AssignmentReplyAttachment> getBeanClass() {
        return AssignmentReplyAttachment.class;
    }
}
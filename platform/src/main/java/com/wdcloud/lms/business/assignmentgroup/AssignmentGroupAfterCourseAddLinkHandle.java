package com.wdcloud.lms.business.assignmentgroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.server.frame.MessageUtil;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 课程创建后创建默认作业组
 */
@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class AssignmentGroupAfterCourseAddLinkHandle implements IDataLinkedHandle {
    @Autowired
    private AssignmentGroupDao assignmentGroupDao;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        AssignmentGroup assignmentGroup = new AssignmentGroup();
        assignmentGroup.setCourseId(courseId);
        String defaultAssignmentGroupName = MessageUtil.getMessage("assignment-group.default.name", WebContext.getUserLocale());
        assignmentGroup.setName(defaultAssignmentGroupName);
        assignmentGroup.setSeq(1);//默认排序开始
        assignmentGroupDao.save(assignmentGroup);

        log.info("[AssignmentGroupAfterCourseAddLinkHandle] add course default assignment group, courseId={}, assignmentGroupId={}, name={}",
                courseId, assignmentGroup.getId(), assignmentGroup.getName());
        return linkedInfo;
    }
}

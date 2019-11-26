package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.server.frame.MessageUtil;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class StudyGroupSetAfterCourseAddHandle implements IDataLinkedHandle {

    @Autowired
    private StudyGroupSetDao studyGroupSetDao;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        StudyGroupSet studyGroupSet = StudyGroupSet.builder()
                .courseId(courseId)
                .allowSelfSignup(Status.YES.getStatus())
                .isSectionGroup(Status.NO.getStatus())
                .isStudentGroup(Status.YES.getStatus())
                .leaderSetStrategy(LeaderSetStrategyEnum.FIRST_JOIN.getStrategy())
                .name(MessageUtil.getMessage("student-study-group-set-name"))
                .build();
        studyGroupSetDao.save(studyGroupSet);
        log.info("[StudyGroupSetAfterCourseAddHandle] add student study group set, courseId={}, studyGroupSetId={}",
                courseId, studyGroupSet.getId());
        return linkedInfo;
    }
}

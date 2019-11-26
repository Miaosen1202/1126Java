package com.wdcloud.lms.business.discussion;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.DiscussionConfigDao;
import com.wdcloud.lms.core.base.model.DiscussionConfig;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 课程添加后添加讨论配置
 */
@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class DiscussionConfigAfterCourseAddLinkedHandle implements IDataLinkedHandle {
    @Autowired
    private DiscussionConfigDao discussionConfigDao;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        DiscussionConfig config = DiscussionConfig.builder()
                .courseId(courseId)
                .userId(WebContext.getUserId())
                .build();
        discussionConfigDao.save(config);

        log.info("[DiscussionConfigAfterCourseAddLinkedHandle] after discussion after course add, courseId={}, discussionId={}",
                courseId, config.getId());
        return linkedInfo;
    }
}

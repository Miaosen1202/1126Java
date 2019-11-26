package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CourseConfigDao;
import com.wdcloud.lms.core.base.model.CourseConfig;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.RegistryCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 新增课程关联处理
 */
@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class CourseConfigAfterCourseAddLinkedHandle implements IDataLinkedHandle {

    @Autowired
    private CourseConfigDao courseConfigDao;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        CourseConfig courseConfig = new CourseConfig();
        courseConfig.setCourseId(courseId);
        courseConfig.setOpenRegistryCode(RegistryCodeUtil.generateRegistryCode(courseId));
        courseConfigDao.save(courseConfig);
        log.info("[CourseConfigAfterCourseAddLinkedHandle] add course config, courseId={}, configId={}",
                courseId, courseConfig.getId());
        return linkedInfo;
    }
}

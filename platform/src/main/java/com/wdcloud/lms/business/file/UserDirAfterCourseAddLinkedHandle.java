package com.wdcloud.lms.business.file;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileAccessStrategyEnum;
import com.wdcloud.lms.core.base.enums.FileSpaceTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.TreeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 课程创建后自动创建课程文件目录
 */
@Slf4j
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_COURSE,
        operateType = OperateType.ADD
)
public class UserDirAfterCourseAddLinkedHandle implements IDataLinkedHandle {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserFileService userFileService;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long courseId = Long.parseLong(linkedInfo.masterId);
        Course course = courseDao.get(courseId);

        userFileService.initCourseSpaceDir(course.getId(), course.getName());

        return linkedInfo;
    }
}

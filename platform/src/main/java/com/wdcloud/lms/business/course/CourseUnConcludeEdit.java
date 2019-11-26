package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 结束课程
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_UNCONCLUDE
)
public class CourseUnConcludeEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseDao courseDao;

    /**
     * @api {post} /course/unConclude/edit 重启课程
     * @apiDescription 重启课程
     * @apiName CourseUnConclude
     * @apiGroup Course
     * @apiParam {Number} id 课程ID
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 课程ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Course course = JSON.parseObject(dataEditInfo.beanJson, Course.class);
        if (course.getId() == null) {
            throw new ParamErrorException();
        }

        courseDao.unconclude(course.getId());
        log.info("[CourseConcludeEdit] user conclude course, courseId={}, userId={}", course.getId(), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(course.getId()));
    }
}

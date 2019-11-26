package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_COURSE_DATE
)
public class CourseDateQuery implements ISelfDefinedSearch<Course> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private TermDao termDao;

    /**
     * @param condition
     * @return
     * @api {get} /course/date/query 课程时间查询
     * @apiDescription 课程时间查询
     * @apiName courseDateQuery
     * @apiGroup Course
     * @apiParam {Object} courseId 课程ID
     * @apiExample {curl} 请求示例:
     * /course/date/query?courseId=1
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} vo 课程时间
     * @apiSuccess {Number} [startTime] 开始时间（时间戳）
     * @apiSuccess {Number} [endTime] 结束时间
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * }
     */
    @Override
    public Course search(Map<String, String> condition) {
        Course course = courseDao.get(Long.valueOf(condition.get("courseId")));
        if (course == null) {
            throw new ParamErrorException();
        }
        if (course.getStartTime() == null || course.getEndTime() == null) {
            Term term = termDao.get(course.getTermId());
            if (course.getStartTime() == null) {
                course.setStartTime(term.getStartTime());
            }
            if (course.getEndTime() == null) {
                course.setEndTime(term.getEndTime());
            }
        }
        return course;
    }
}

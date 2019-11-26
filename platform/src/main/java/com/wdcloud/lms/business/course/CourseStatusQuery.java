package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.course.vo.CourseJoinedQueryVo;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.lms.core.base.vo.CourseSubmitCountVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_PUBLIC_STATUS
)
public class CourseStatusQuery implements ISelfDefinedSearch<Course> {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;

    /**
     * @api {get} /course/publicStatus/query 课程发布状态查询
     * @apiName CoursePublicStatusQuery
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 描述
     * @apiSuccess {Object} [entity] 课程状态
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     * @apiSuccess {Number=0,1} entity.status 课程发布状态
     * @apiSuccess {Number} entity.gradeTaskSubmittedCount 已提交评分任务数量
     */
    @Override
    public Course search(Map<String, String> condition) {
        String cid = condition.get(Constants.PARAM_COURSE_ID);
        if (StringUtil.isEmpty(cid)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(cid);
        Course course = courseDao.get(courseId);

        CourseJoinedVo courseJoinedVo = new CourseJoinedVo();
        if (course != null) {
            List<CourseSubmitCountVo> courseGradeTaskSubmitCounts = userSubmitRecordDao.getCourseGradeTaskSubmitCount(List.of(courseId));
            if (ListUtils.isNotEmpty(courseGradeTaskSubmitCounts)) {
                courseJoinedVo.setGradeTaskSubmittedCount(courseGradeTaskSubmitCounts.get(0).getSubmitCount());
            }
        }

        return courseJoinedVo;
    }
}
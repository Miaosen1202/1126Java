package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.lms.core.base.vo.CourseSubmitCountVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户查询参与的课程
 * 教师、助教可以看到所有自己参与的课程
 * 学生自能看到已发布的参与的课程
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_HOMEPAGE
)
public class CourseHomepageQuery implements ISelfDefinedSearch<CourseJoinedVo> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;

    /**
     *
     * @api {get} /course/homepage/query 课程首页查询
     * @apiDescription 课程首页发布状态查询
     * @apiName courseHomeQuery
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} entity 加入的课程
     * @apiSuccess {String} entity.id 课程ID
     * @apiSuccess {Number=0,1} entity.status 课程发布状态
     * @apiSuccess {Number=0,1} entity.isConcluded 课程是否结束
     * @apiSuccess {Number} entity.gradeTaskSubmittedCount 已提交评分任务数量
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": {
     *
     *     }
     * }
     */
    @Override
    public CourseJoinedVo search(Map<String, String> condition) {
        Course course = courseDao.findOne(Course.builder().isDeleted(Status.NO.getStatus())
                .id(Long.valueOf(condition.get(Constants.PARAM_COURSE_ID))).build());
        CourseJoinedVo courseJoinedVos = BeanUtil.beanCopyProperties(course,CourseJoinedVo.class);
        Map<Long, Long> courseGradeTaskSubmitCountMap = userSubmitRecordDao.getCourseGradeTaskSubmitCount(List.of(course.getId()))
                .stream()
                .collect(Collectors.toMap(CourseSubmitCountVo::getCourseId, CourseSubmitCountVo::getSubmitCount));
        Long count = courseGradeTaskSubmitCountMap.get(course.getId());
        if (count != null) {
            courseJoinedVos.setGradeTaskSubmittedCount(count);
        }
        return courseJoinedVos;
    }

}

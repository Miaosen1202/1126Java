package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.course.enums.CourseVisibilityEnum;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseConfig;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.vo.CoursePublicVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_PUBLIC
)
public class PublicCourseQuery implements ISelfDefinedSearch<List<CoursePublicVo>> {
    @Autowired
    private CourseDao courseDao;

    /**
     * @api {get} /course/public/query 公共课程列表
     * @apiDescription 公共课程查询
     * @apiName CoursePublicQuery
     * @apiGroup Course
     *
     * @apiParam {String} [name] 课程名称
     * @apiParam {Number=0,1} [filterOpenRegistry] 开放注册
     * @apiParam {Number=0,1} [filterPublic] 公开
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} [entity] 课程列表
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     * @apiSuccess {Number=1,2} entity.visibility 可见性, 1: 公开, 2: 课程专属
     * @apiSuccess {Number=0,1} entity.allowJoin 是否用户可以直接加入
     * @apiSuccess {Number=0,1} entity.allowOpenRegistry 开放注册
     * @apiSuccess {Number} [entity.joinedStudentNum] 用户作为学生加入课程下班级的数量
     *
     */
    @Override
    public List<CoursePublicVo> search(Map<String, String> condition) {
        Map<String, Object> params = parseParam(condition);

        return courseDao.findPublicCourses(params);
    }

    private Map<String, Object> parseParam(Map<String, String> condition) {
        Map<String, Object> param = new HashMap<>();

        String name = condition.get(Constants.PARAM_NAME);
        if (StringUtil.isNotEmpty(name)) {
            param.put(Course.NAME, name);
        }

        String filterPublic = condition.get(Constants.PARAM_FILTER_PUBLIC);
        if (StringUtil.isNotEmpty(filterPublic)) {
            if (Boolean.parseBoolean(filterPublic)) {
                param.put(Course.VISIBILITY, CourseVisibilityEnum.PUBLIC.getVisibility());
            }
        }

        String filterOpenRegistry = condition.get(Constants.PARAM_FILTER_OPEN_REGISTRY);
        if (StringUtil.isNotEmpty(filterOpenRegistry)) {
            if (Boolean.parseBoolean(filterOpenRegistry)) {
                param.put(CourseConfig.ALLOW_OPEN_REGISTRY, Status.YES.getStatus());
            }
        }

        param.put(CourseUser.USER_ID, WebContext.getUserId());
        return param;
    }
}

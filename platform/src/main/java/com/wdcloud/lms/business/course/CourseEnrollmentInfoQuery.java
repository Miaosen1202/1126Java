package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CourseConfigDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseConfig;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_ENROLLMENT_INFO
)
public class CourseEnrollmentInfoQuery implements ISelfDefinedSearch<CourseEnrollmentInfoQuery.CourseEnrollmentInfo> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private SectionUserDao sectionUserDao;

    /**
     * @api {post} /course/enrollmentInfo/query 使用注册code查询课程信息
     * @apiName CourseEnrollmentInfoQuery
     * @apiGroup Course
     *
     *
     * @apiParam {String} code 开放注册码
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息
     * @apiSuccess {Object} [entity] 课程信息
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     * @apiSuccess {Number=0,1} entity.publicStatus 发布状态
     * @apiSuccess {Number=0,1} entity.isConclude 是否已结束
     * @apiSuccess {Number=0,1} entity.isDeleted 是否已删除
     */
    @Override
    public CourseEnrollmentInfo search(Map<String, String> condition) {

        String code = condition.get(Constants.PARAM_CODE);
        if (StringUtil.isEmpty(code)) {
            throw new ParamErrorException();
        }

        CourseConfig courseConfig = courseConfigDao.findOne(CourseConfig.builder().openRegistryCode(code).build());
        if (courseConfig == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_CODE, code);
        }
        Course course = courseDao.get(courseConfig.getCourseId());

        CourseEnrollmentInfo enrollmentInfo = CourseEnrollmentInfo.builder()
                .id(course.getId())
                .name(course.getName())
                .publicStatus(course.getStatus())
                .isConclude(course.getIsConcluded())
                .isDeleted(course.getIsDeleted())
                .build();

        return enrollmentInfo;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class CourseEnrollmentInfo {
        private Long id;
        private String name;
        private Integer publicStatus;
        private Integer isConclude;
        private Integer isDeleted;
    }
}

package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询用户邀请中信息
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_REGISTERING
)
public class CourseUserRegisteringQuery implements ISelfDefinedSearch<List<CourseUserRegisteringQuery.CourseUserJoinPendingDetail>> {

    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private RoleService roleService;

    /**
     *
     * @api {get} /courseUser/registering/query
     * @apiDescription 查询课程发布的用户邀请中信息
     * @apiName CourseUserRegisteringQuery
     * @apiGroup People
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {Object[]} entity 邀请信息
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.courseName 课程名称
     * @apiSuccess {Number} entity.sectionId 班级ID
     * @apiSuccess {String} entity.sectionName 班级名称
     * @apiSuccess {Number} entity.roleId 角色ID
     * @apiSuccess {String} entity.roleName 角色名称
     * @apiSuccess {String} entity.code 邀请码
     *
     */
    @Override
    public List<CourseUserJoinPendingDetail> search(Map condition) {
        // 查询已发布课程的邀请信息
        List<CourseUserJoinPending> userJoinPendings = courseUserJoinPendingDao.find(CourseUserJoinPending.builder()
                .userId(WebContext.getUserId())
                .publicStatus(Status.YES.getStatus())
                .build());

        List<Long> courseIds = userJoinPendings.stream().map(CourseUserJoinPending::getCourseId).collect(Collectors.toList());
        List<Long> sectionIds = userJoinPendings.stream().map(CourseUserJoinPending::getSectionId).collect(Collectors.toList());

        List<Course> courses = courseDao.gets(courseIds);
        Map<Long, String> courseIdAndNameMap = courses.stream().collect(Collectors.toMap(Course::getId, Course::getName));

        List<Section> sections = sectionDao.gets(sectionIds);
        Map<Long, String> sectionIdAndNameMap = sections.stream().collect(Collectors.toMap(Section::getId, Section::getName));

        Map<Long, String> roleIdAndNameMap = roleService.findRoleIdAndNameMap();

        List<CourseUserJoinPendingDetail> result = BeanUtil.beanCopyPropertiesForList(userJoinPendings, CourseUserJoinPendingDetail.class);
        for (CourseUserJoinPendingDetail pendingDetail : result) {
            pendingDetail.setCourseName(courseIdAndNameMap.get(pendingDetail.getCourseId()));
            pendingDetail.setSectionName(sectionIdAndNameMap.get(pendingDetail.getSectionId()));
            pendingDetail.setRoleName(roleIdAndNameMap.get(pendingDetail.getRoleId()));
        }
        return result;
    }

    @Data
    public static class CourseUserJoinPendingDetail extends CourseUserJoinPending {
        private String courseName;
        private String sectionName;
        private String roleName;
    }
}

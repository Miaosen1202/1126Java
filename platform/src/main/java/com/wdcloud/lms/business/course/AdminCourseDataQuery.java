package com.wdcloud.lms.business.course;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.CourseListVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_ADMIN_COURSE)
public class AdminCourseDataQuery implements IDataQueryComponent<CourseListVO> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;


    /**
     * @api {get} /adminCourse/pageList 课程列表分页查询接口
     * @apiDescription 课程列表分页查询接口
     * @apiName adminCoursePageList
     * @apiGroup admin
     * @apiParam {String=course,teacher} type  查询类型
     * @apiParam {Number} [termId] 学期ID
     * @apiParam {Number} [name] 名称
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 条数
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 收藏课程列表
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     * @apiSuccess {String} [entity.courseAlias] 课程别名（用户为自己加入的课程创建的昵称）
     * @apiSuccess {String} entity.code 课程编码
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "entity": {
     * <p>
     * }
     * }
     */
    @Override
    public PageQueryResult<CourseListVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        Map<String, Object> map = Maps.newHashMap();
        map.put(Course.IS_DELETED, Status.NO);
        map.put(Course.ORG_TREE_ID, WebContext.getOrgTreeId());
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_TERM_ID))) {
            map.put(Constants.PARAM_TERM_ID, param.get(Constants.PARAM_TERM_ID));
        }
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_NAME))) {
            map.put(User.USERNAME, "%" + param.get(Constants.PARAM_NAME) + "%");
            map.put(Course.NAME, "%" + param.get(Constants.PARAM_NAME) + "%");
        }
        if (Constants.PARAM_VALUE_COURSE.equalsIgnoreCase(param.get(Constants.PARAM_TYPE))) {
            //根据课程查询
            return getCoursePageQueryResult(map, pageIndex, pageSize);
        }
        //根据教师查询
        if (Constants.PARAM_VALUE_TEACHER.equalsIgnoreCase(param.get(Constants.PARAM_TYPE))) {
            return getTeacherPageQueryResult(map, pageIndex, pageSize);
        }
        return null;
    }

    private PageQueryResult<CourseListVO> getTeacherPageQueryResult(Map<String, Object> param, int pageIndex, int pageSize) {
        Page<CourseListVO> courses = (Page<CourseListVO>) courseDao.getCourseByUsernameAndTermId(param);
        return setData(pageIndex, pageSize, courses);
    }

    private PageQueryResult<CourseListVO> getCoursePageQueryResult(Map<String, Object> param, int pageIndex, int pageSize) {
        Page<CourseListVO> courses = (Page<CourseListVO>) courseDao.getCourseByNameAndTermId(param);
        return setData(pageIndex, pageSize, courses);
    }

    private PageQueryResult<CourseListVO> setData(int pageIndex, int pageSize, Page<CourseListVO> courses) {
        for (CourseListVO course : courses) {
            List<User> userBySectionTable = userDao.getUserBySectionTable(course.getId(), roleService.getTeacherRoleId());
            course.setTeachers(userBySectionTable);
            course.setStudentCount(sectionUserDao.count(SectionUser.builder().courseId(course.getId()).roleId(roleService.getStudentRoleId()).build()));
        }
        return new PageQueryResult<>(courses.getTotal(), courses, pageSize, pageIndex);
    }

}

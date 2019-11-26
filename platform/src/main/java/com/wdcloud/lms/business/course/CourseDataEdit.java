package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.course.enums.CourseCreateModeEnum;
import com.wdcloud.lms.business.course.vo.CourseAddVo;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_COURSE)
public class CourseDataEdit implements IDataEditComponent {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgDao orgDao;

    /**
     * @api {post} /course/add 课程添加
     * @apiDescription 课程添加
     * @apiName courseAdd
     * @apiGroup Course
     * @apiParam {String={..500}} name 课程名称
     * @apiParam {String} [code] 课程编码
     * @apiParam {String} [description] 描述
     * @apiParam {String} [coverColor] 前景色
     * @apiParam {String} [alias] 用户为课程设置昵称
     * @apiParam {Number=0,1} [status] 是否发布: 1. 发布，0. 不发布
     * @apiParam {Number=1,2} [visibility] 可见性: 1. 公开，2. 课程，3. 机构(预留)
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     * @apiParam {Number} [orgId] 机构ID，仅在管理员添加课程时必填，其他情况均不填
     * @apiParam {Number} [orgTreeId] 所属机构tree_id，仅在管理员添加课程时必填，其他情况均不填
     * @apiParam {Number} [termId] 学期ID，仅在管理员添加课程时必填，其他情况均不填
     * @apiExample {json} 请求示例:
     * {
     *   "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增课程ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        CourseAddVo course = JSON.parseObject(dataEditInfo.beanJson, CourseAddVo.class);
        if (StringUtil.isEmpty(course.getCode())) {
            course.setCode(course.getName());
        }

        Org alongSchool = orgDao.get(WebContext.getOrgId());
        while (!Objects.equals(alongSchool.getType(), OrgTypeEnum.SCHOOL.getType())
                && !Objects.equals(alongSchool.getParentId(), Constants.TREE_ROOT_PARENT_ID)) {
            alongSchool = orgDao.get(alongSchool.getParentId());
        }

        if (!Objects.equals(alongSchool.getType(), OrgTypeEnum.SCHOOL.getType())) {
            throw new BaseException("create-course-but-not-in-school");
        }

        if (course.getTermId() == null) {
            Term defaultTerm = termDao.findDefaultTerm(alongSchool.getId());
            course.setTermId(defaultTerm.getId());
        }
        course.setCreateMode(CourseCreateModeEnum.CREATED.getMode());
        if (roleService.isAdmin()) {
            course.setOrgId(course.getOrgId());
            course.setOrgTreeId(course.getOrgTreeId());
        } else {
            course.setOrgId(WebContext.getOrgId());
            course.setOrgTreeId(WebContext.getOrgTreeId());
        }

        courseDao.save(course);

        log.info("[CourseDataEdit] add course success, courseId={}, name={}", course.getId(), course.getName());
        return new LinkedInfo(String.valueOf(course.getId()), dataEditInfo.beanJson);
    }

    /**
     * @param dataEditInfo
     * @return
     * @api {post} /course/modify 课程修改
     * @apiDescription 课程修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName courseModify
     * @apiGroup Course
     * @apiParam {Number} id 课程ID
     * @apiParam {String} [name] 课程名称
     * @apiParam {String} [code] 课程编码
     * @apiParam {String} [alias] 用户为课程设置昵称
     * @apiParam {String} [description] 描述
     * @apiParam {String} [coverColor] 前景色
     * @apiParam {Number} [coverFileId] 封面图ID
     * @apiParam {Number=0,1} [status] 是否发布: 1. 发布，0. 不发布
     * @apiParam {Number=1,2} [visibility] 可见性: 1. 公开，2. 课程，3. 机构(预留)
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     * @apiExample {json} 请求示例:
     * {
     * "id": 1,
     * "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改课程ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        CourseAddVo course = JSON.parseObject(dataEditInfo.beanJson, CourseAddVo.class);
        courseDao.update(course);

        String alias = course.getAlias();
        if (alias != null) {
            courseUserDao.updateUserCourseAlias(WebContext.getUserId(), course.getId(), alias);
        }
        log.info("[CourseDataEdit] update course success, course={}, user={}", JSON.toJSONString(course), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(course.getId()));
    }

    /**
     * @param dataEditInfo
     * @return
     * @api {post} /course/deletes 课程删除
     * @apiDescription 课程删除
     * @apiName courseDeletes
     * @apiGroup Course
     * @apiParam {String[]} ids ID列表
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除课程ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);

        courseDao.updateByExample(Course.builder().isDeleted(Status.YES.getStatus()).build(), ids);
        log.info("[CourseDataEdit] update course success, courseIds={}, user={}", JSON.toJSONString(ids), WebContext.getUserId());
        return new LinkedInfo(dataEditInfo.beanJson);
    }
}

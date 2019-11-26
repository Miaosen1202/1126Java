package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.LoginRecordDao;
import com.wdcloud.lms.core.base.model.LoginRecord;
import com.wdcloud.lms.core.base.vo.CourseUserDetailVo;
import com.wdcloud.lms.core.base.vo.SectionUserDetailVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_USER
)
public class CourseUserDetailQuery implements ISelfDefinedSearch<List<CourseUserDetailVo>> {
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {get} /course/user/query 课程用户查询
     * @apiDescription 查询课程下用户
     * @apiName courseUserQuery
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} [roleId] 角色
     * @apiParam {String} [name] 名称（登陆名或昵称模糊查询）
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} [entity.sisId] SIS ID
     * @apiSuccess {Number} entity.username 登录名
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} [entity.avatarFileUrl] 头像地址
     * @apiSuccess {String} [entity.fullName] 用户全名
     * @apiSuccess {Number=0,1} entity.isActive 是否激活
     * @apiSuccess {Object[]} entity.sectionUserDetailVos 用户所属班级信息
     * @apiSuccess {Number=1,2} entity.sectionUserDetailVos.registryStatus 在班级注册状态, 1: 已邀请　2: 已加入
     * @apiSuccess {Number} entity.sectionUserDetailVos.registryOrigin 在班级注册来源, 1: 管理员添加 2: 邀请加入　3: 自行加入
     * @apiSuccess {Number} entity.sectionUserDetailVos.createTime 在班级注册时间
     * @apiSuccess {Number} entity.sectionUserDetailVos.sectionId 班级
     * @apiSuccess {Number} entity.sectionUserDetailVos.sectionName 班级名称
     * @apiSuccess {Number} entity.sectionUserDetailVos.roleId 角色
     * @apiSuccess {Number} entity.sectionUserDetailVos.roleName 角色名称
     *
     * @apiSuccess {Number} entity.lastActivity 最近活跃时间
     * @apiSuccess {Number} entity.totalActivity 总活跃时间（暂时不展示该字段）
     */
    @Override
    public List<CourseUserDetailVo> search(Map<String, String> condition) {
        if (!condition.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        List<CourseUserDetailVo> courseUserDetail = courseUserDao.findCourseUserDetail(parseParam(condition));

        return courseUserDetail;
    }

    private Map<String, Object> parseParam(Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        long courseId = Long.parseLong(params.get(Constants.PARAM_COURSE_ID));
        result.put(Constants.PARAM_COURSE_ID, courseId);

        String name = params.get(Constants.PARAM_NAME);
        if (StringUtil.isNotEmpty(name)) {
            result.put(Constants.PARAM_NAME, name);
        }

        if (StringUtil.isNotEmpty(params.get(Constants.PARAM_ROLE_ID))) {
            long roleId = Long.parseLong(params.get(Constants.PARAM_ROLE_ID));
            result.put(Constants.PARAM_ROLE_ID, roleId);
        }
        return result;
    }
}

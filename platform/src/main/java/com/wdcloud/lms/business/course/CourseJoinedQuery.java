package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.TermService;
import com.wdcloud.lms.business.course.transfer.CourseTransferService;
import com.wdcloud.lms.business.course.vo.CourseJoinedQueryVo;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.lms.core.base.vo.CourseSubmitCountVo;
import com.wdcloud.lms.core.base.vo.TermAndConfigVo;
import com.wdcloud.lms.core.base.vo.Tuple;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户查询参与的课程
 * 教师、助教可以看到所有自己参与的课程
 * 学生自能看到已发布的参与的课程
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_JOINED
)
public class CourseJoinedQuery implements ISelfDefinedSearch<CourseJoinedQueryVo> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseTransferService courseTransferService;
    @Autowired
    private TermDao termDao;
    @Autowired
    private TermService termService;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;

    /**
     *
     * @api {get} /course/joined/query 参与课程查询
     * @apiDescription 查询用户参与的课程（所有课程界面）
     * @apiName courseJoinedQuery
     * @apiGroup Course
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} entity 加入的课程
     * @apiSuccess {Object[]} [entity.currentEnrollments] 当前课程
     * @apiSuccess {Number} entity.currentEnrollments.id 课程ID
     * @apiSuccess {String} entity.currentEnrollments.name 课程名称
     * @apiSuccess {String} [entity.currentEnrollments.courseAlias] 课程别名（用户为自己加入的课程创建的昵称）
     * @apiSuccess {String} entity.currentEnrollments.code 课程编码
     * @apiSuccess {Number=0,1} entity.currentEnrollments.isFavorite 是否收藏
     * @apiSuccess {Number=0,1} entity.currentEnrollments.status 课程发布状态
     * @apiSuccess {Number=0,1} entity.currentEnrollments.isActiveToUser 课程用户是否激活
     * @apiSuccess {Number=0,1,2} entity.currentEnrollments.statusToUser 课程用户状态；其中0：结课，1：冻结，2：可用
     * @apiSuccess {String} [entity.currentEnrollments.coverColor] 课程封面色
     * @apiSuccess {Object} [entity.currentEnrollments.term] 课程学期
     * @apiSuccess {Number} entity.currentEnrollments.term.id 课程学期ID
     * @apiSuccess {String} entity.currentEnrollments.term.name 课程学期名称
     * @apiSuccess {String} [entity.currentEnrollments.term.code] 课程学期编码
     * @apiSuccess {Number} [entity.currentEnrollments.term.startTime] 课程学期开始时间
     * @apiSuccess {Number} [entity.currentEnrollments.term.endTime] 课程学期结束时间
     * @apiSuccess {Number=0,1} entity.currentEnrollments.allowOpenRegistry 开放注册
     * @apiSuccess {String} [entity.currentEnrollments.openRegistryCode] 开放注册码
     * @apiSuccess {Number} entity.currentEnrollments.gradeTaskSubmittedCount 已提交评分任务数量
     *
     * @apiSuccess {Object[]} [entity.priorEnrollments] 已结束的课程
     * @apiSuccess {Number} entity.priorEnrollments.id 课程ID
     * @apiSuccess {String} entity.priorEnrollments.name 课程名称
     * @apiSuccess {String} [entity.priorEnrollments.courseAlias] 课程别名（用户为自己加入的课程创建的昵称）
     * @apiSuccess {String} entity.priorEnrollments.code 课程编码
     * @apiSuccess {Number=0,1} entity.priorEnrollments.isFavorite 是否收藏
     * @apiSuccess {Number=0,1} entity.priorEnrollments.status 课程发布状态
     * @apiSuccess {Number=0,1} entity.currentEnrollments.isActiveToUser 课程用户是否激活
     * @apiSuccess {Number=0,1,2} entity.currentEnrollments.statusToUser 课程用户状态；其中0：结课，1：冻结，2：可用
     * @apiSuccess {String} [entity.priorEnrollments.coverColor] 课程封面色
     * @apiSuccess {Object} [entity.priorEnrollments.term] 课程学期
     * @apiSuccess {Number} entity.priorEnrollments.term.id 课程学期ID
     * @apiSuccess {String} entity.priorEnrollments.term.name 课程学期名称
     * @apiSuccess {String} [entity.priorEnrollments.term.code] 课程学期编码
     * @apiSuccess {Number} [entity.priorEnrollments.term.startTime] 课程学期开始时间
     * @apiSuccess {Number} [entity.priorEnrollments.term.endTime] 课程学期结束时间
     * @apiSuccess {Number=0,1} entity.priorEnrollments.allowOpenRegistry 开放注册
     * @apiSuccess {String} [entity.priorEnrollments.openRegistryCode] 开放注册码
     * @apiSuccess {Number} entity.priorEnrollments.gradeTaskSubmittedCount 已提交评分任务数量
     *
     * @apiSuccess {Object[]} [entity.futureEnrollments] 未开始的课程
     * @apiSuccess {Number} entity.futureEnrollments.id 课程ID
     * @apiSuccess {String} entity.futureEnrollments.name 课程名称
     * @apiSuccess {String} [entity.futureEnrollments.courseAlias] 课程别名（用户为自己加入的课程创建的昵称）
     * @apiSuccess {String} entity.futureEnrollments.code 课程编码
     * @apiSuccess {Number=0,1} entity.futureEnrollments.isFavorite 是否收藏
     * @apiSuccess {Number=0,1} entity.futureEnrollments.status 课程发布状态
     * @apiSuccess {Number=0,1} entity.currentEnrollments.isActiveToUser 课程用户是否激活
     * @apiSuccess {Number=0,1,2} entity.currentEnrollments.statusToUser 课程用户状态；其中0：结课，1：冻结，2：可用
     * @apiSuccess {String} [entity.futureEnrollments.coverColor] 课程封面色
     * @apiSuccess {Object} [entity.futureEnrollments.term] 课程学期
     * @apiSuccess {Number} entity.futureEnrollments.term.id 课程学期ID
     * @apiSuccess {String} entity.futureEnrollments.term.name 课程学期名称
     * @apiSuccess {String} [entity.futureEnrollments.term.code] 课程学期编码
     * @apiSuccess {Number} [entity.futureEnrollments.term.startTime] 课程学期开始时间
     * @apiSuccess {Number} [entity.futureEnrollments.term.endTime] 课程学期结束时间
     * @apiSuccess {Number=0,1} entity.futureEnrollments.allowOpenRegistry 开放注册
     * @apiSuccess {String} [entity.futureEnrollments.openRegistryCode] 开放注册码
     * @apiSuccess {Number} entity.futureEnrollments.gradeTaskSubmittedCount 已提交评分任务数量
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
    public CourseJoinedQueryVo search(Map<String, String> condition) {
        Map<String, Object> param = parseParam(condition);
        List<CourseJoinedVo> courseJoinedVos = courseDao.findCourseJoined(param);
        List<Long> termIds = courseJoinedVos.stream().map(CourseJoinedVo::getTermId)
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
        List<Long> courseIds = courseJoinedVos.stream()
                .map(CourseJoinedVo::getId)
                .collect(Collectors.toList());

        Map<Long, TermAndConfigVo> termIdMap = new HashMap<>();
        if (ListUtils.isNotEmpty(termIds)) {
            List<TermAndConfigVo> terms = termDao.findTermAndConfigs(termIds);
            termIdMap = terms.stream().collect(Collectors.toMap(TermAndConfigVo::getId, a -> a, (a, b) -> a));
        }

        if (ListUtils.isNotEmpty(courseIds)) {
            Map<Long, Long> courseGradeTaskSubmitCountMap = userSubmitRecordDao.getCourseGradeTaskSubmitCount(courseIds)
                    .stream()
                    .collect(Collectors.toMap(CourseSubmitCountVo::getCourseId, CourseSubmitCountVo::getSubmitCount));
            for (CourseJoinedVo courseJoinedVo : courseJoinedVos) {
                Long count = courseGradeTaskSubmitCountMap.get(courseJoinedVo.getId());
                if (count != null) {
                    courseJoinedVo.setGradeTaskSubmittedCount(count);
                }
            }
        }

        CourseJoinedQueryVo result = new CourseJoinedQueryVo();
        for (CourseJoinedVo courseJoinedVo : courseJoinedVos) {
            TermAndConfigVo term = courseJoinedVo.getTermId() == null ? null : termIdMap.get(courseJoinedVo.getTermId());
            courseJoinedVo.setTerm(term);
            Integer statusToUser = courseTransferService.getStatusToUser(courseJoinedVo, courseJoinedVo.getIsActiveToUser());
            courseJoinedVo.setStatusToUser(statusToUser);
            if (isPast(courseJoinedVo)) {
                result.getPriorEnrollments().add(courseJoinedVo);
            } else if (isFuture(courseJoinedVo)) {
                result.getFutureEnrollments().add(courseJoinedVo);
            } else {
                result.getCurrentEnrollments().add(courseJoinedVo);
            }
        }

        return result;
    }

    private boolean isFuture(CourseJoinedVo joinedVo) {
        Date now = new Date();

        if (joinedVo.getStartTime() != null) {
            if (now.before(joinedVo.getStartTime())) {
                return true;
            }
        } else {
            TermAndConfigVo termAndConfigVo = joinedVo.getTerm();
            if (termAndConfigVo != null) {
                Tuple<Date, Date> termRoleStartAndEndTime
                        = termService.getTermRoleStartAndEndTime(termAndConfigVo, WebContext.getRoleId());
                if (termRoleStartAndEndTime.getOne() != null
                        && now.before(termRoleStartAndEndTime.getOne())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPast(CourseJoinedVo joinedVo) {
        Date now = new Date();

        if (joinedVo.getEndTime() != null) {
            if (now.after(joinedVo.getEndTime())) {
                return true;
            }
        } else {
            TermAndConfigVo termAndConfigVo = joinedVo.getTerm();
            if (termAndConfigVo != null) {
                Tuple<Date, Date> termRoleStartAndEndTime = termService.getTermRoleStartAndEndTime(termAndConfigVo, WebContext.getRoleId());
                if (termRoleStartAndEndTime.getTwo() != null
                        && now.after(termRoleStartAndEndTime.getTwo())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<String, Object> parseParam(Map<String, String> condition) {
        Map<String, Object> result = new HashMap<>();
        result.put(Constants.PARAM_USER_ID, WebContext.getUserId());
        result.put(Constants.PARAM_ROLE_ID, WebContext.getRoleId());
        result.put(Constants.PARAM_RESOURCE_STATUS, UserRegistryStatusEnum.JOINED.getStatus());
        return result;
    }
}

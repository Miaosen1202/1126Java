package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.course.transfer.CourseTransferService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.CourseFavoriteVo;
import com.wdcloud.lms.core.base.vo.GradeTodoVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 收藏课程查询(Dashboard以及左侧边栏）
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_FAVORITE
)
public class CourseFavoriteQuery implements ISelfDefinedSearch<List<? extends Course>> {
    @Autowired
    private UserTodoDao userTodoDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseTransferService courseTransferService;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private RoleService roleService;

    /**
     *
     * @api {get} /course/favorite/query 用户收藏课程查询
     * @apiDescription 查询用户收藏的课程，Dashboard，右侧边烂使用接口
     * @apiName courseFavoriteQuery
     * @apiGroup Course
     *
     * @apiParam {Number=0,1} [includeExt] 是否返回扩展信息（默认不返回）
     * @apiParam {Number=0,1} [isSort] 是否排序返回（默认不排序）
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 收藏课程列表
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     * @apiSuccess {String} [entity.courseAlias] 课程别名（用户为自己加入的课程创建的昵称）
     * @apiSuccess {String} entity.code 课程编码
     * @apiSuccess {String} [entity.description] 课程简介
     * @apiSuccess {Number=0,1} entity.status 课程发布状态
     * @apiSuccess {Number=0,1} entity.isActiveToUser 课程用户是否激活
     * @apiSuccess {Number=0,1,2} entity.statusToUser 课程用户状态；其中0：结课，1：冻结，2：可用
     * @apiSuccess {String} [entity.coverColor] 课程封面色
     * @apiSuccess {String} [entity.coverFileUrl] 课程封面图URL
     * @apiSuccess {Number} [entity.seq] 排序
     * @apiSuccess {Number} [entity.startTime] 开始时间
     * @apiSuccess {Number} [entity.endTime] 结束时间
     * @apiSuccess {Number=0,1} entity.isConclude　是否结束
     * @apiSuccess {Object} [entity.term] 课程学期
     * @apiSuccess {Number} entity.term.id 课程学期ID
     * @apiSuccess {String} entity.term.name 课程学期名称
     * @apiSuccess {String} [entity.term.code] 课程学期编码
     * @apiSuccess {Number} [entity.term.startTime] 课程学期开始时间
     * @apiSuccess {Number} [entity.term.endTime] 课程学期结束时间
     * @apiSuccess {Object} [entity.favoriteExt] 扩展信息
     * @apiSuccess {Number} entity.favoriteExt.assignmentTodoNumber 作业待办数量
     * @apiSuccess {Number} entity.favoriteExt.discussionTodoNumber 讨论待办数量
     * @apiSuccess {Number} entity.favoriteExt.quizTodoNumber 测验待办数量
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
    public List<? extends Course> search(Map<String, String> condition) {
        Map<String, Object> param = parseParam(condition);

        List<CourseFavoriteVo> courseFavorites = courseDao.findCourseFavorites(param);

        for(CourseFavoriteVo courseFavoriteVo : courseFavorites){
            Integer statusToUser = courseTransferService.getStatusToUser(courseFavoriteVo, courseFavoriteVo.getIsActiveToUser());
            courseFavoriteVo.setStatusToUser(statusToUser);
        }

        List<Long> courseIds = courseFavorites.stream().map(CourseFavoriteVo::getId).collect(Collectors.toList());
        if (Objects.equals(param.get(Constants.PARAM_INCLUDE_EXT), Status.YES.getStatus())) {
            List<GradeTodoVo> gradeTodoVos = new ArrayList<GradeTodoVo>();
            // 教师返回待评分任务数量
            if (roleService.hasTeacherOrTutorRole()) {
                gradeTodoVos = userSubmitRecordDao.queryGradeTodo(courseIds);
            } else {
                //学生返回待提交任务数量
                gradeTodoVos = findToBeSubmitCountsByCourseIds(courseIds);
            }
            Map<Long, List<GradeTodoVo>> gradeTodosCourseIdMap = gradeTodoVos.stream().collect(Collectors.groupingBy(GradeTodoVo::getCourseId));

            for (CourseFavoriteVo courseFavorite : courseFavorites) {
                if (courseFavorite.getFavoriteExt() == null) {
                    courseFavorite.setFavoriteExt(new CourseFavoriteVo.CourseFavoriteExt());
                }
                List<GradeTodoVo> courseGradeTodos = gradeTodosCourseIdMap.get(courseFavorite.getId());
                if (ListUtils.isEmpty(courseGradeTodos)) {
                    continue;
                }

                CourseFavoriteVo.CourseFavoriteExt favoriteExt = courseFavorite.getFavoriteExt();
                for (GradeTodoVo gradeTodoVo : courseGradeTodos) {
                    if (Objects.equals(gradeTodoVo.getOriginType(), OriginTypeEnum.ASSIGNMENT.getType())) {
                        favoriteExt.setAssignmentTodoNumber(gradeTodoVo.getTodoNumber());
                    } else if (Objects.equals(gradeTodoVo.getOriginType(), OriginTypeEnum.DISCUSSION.getType())) {
                        favoriteExt.setDiscussionTodoNumber(gradeTodoVo.getTodoNumber());
                    } else if (Objects.equals(gradeTodoVo.getOriginType(), OriginTypeEnum.QUIZ.getType())) {
                        favoriteExt.setQuizTodoNumber(gradeTodoVo.getTodoNumber());
                    }
                }
            }
        }
        return courseFavorites;
    }

    private List<GradeTodoVo> findToBeSubmitCountsByCourseIds(List<Long> courseIds) {
        List<GradeTodoVo> toBeSubmitList = new ArrayList<>();
        List<GradeTodoVo> assignmentList = new ArrayList<>();
        List<GradeTodoVo> discussionList = new ArrayList<>();
        List<GradeTodoVo> quizList = new ArrayList<>();
        if (courseIds.size() > 0) {
            //分配到我并且已经开始的作业列表
            assignmentList = userTodoDao.findToBeSubmitAssignmentCountByCourseId(WebContext.getUserId(),courseIds);
            //分给到我并且已经开始的讨论列表
            discussionList = userTodoDao.findToBeSubmitDiscussionCountByCourseId(WebContext.getUserId(),courseIds);
            //分给到我并且已经开始的测验列表
            quizList = userTodoDao.findToBeSubmitQuizCountByCourseId(WebContext.getUserId(),courseIds);
        }
        toBeSubmitList.addAll(discussionList);
        toBeSubmitList.addAll(assignmentList);
        toBeSubmitList.addAll(quizList);
        toBeSubmitList = toBeSubmitList.stream().filter(x -> x.getCourseId() != null).collect(Collectors.toList());
        return toBeSubmitList;
    }

    private Map<String, Object> parseParam(Map<String, String> condition) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (condition.containsKey(Constants.PARAM_INCLUDE_EXT)) {
                int includeExt = Status.statusOf(Integer.parseInt(condition.get(Constants.PARAM_INCLUDE_EXT))).getStatus();
                result.put(Constants.PARAM_INCLUDE_EXT, includeExt);
            } else {
                result.put(Constants.PARAM_INCLUDE_EXT, Status.NO.getStatus());
            }
            if (condition.containsKey(Constants.PARAM_IS_SORT)) {
                int isSort = Integer.parseInt(condition.get(Constants.PARAM_IS_SORT));
                result.put(Constants.PARAM_IS_SORT, isSort);
            }

            result.put(Constants.PARAM_RESOURCE_STATUS, UserRegistryStatusEnum.JOINED.getStatus());
            result.put(Constants.PARAM_ROLE_ID, WebContext.getRoleId());
            result.put(Constants.PARAM_USER_ID, WebContext.getUserId());

        } catch (Exception e) {
            throw new ParamErrorException();
        }
        return result;
    }
}

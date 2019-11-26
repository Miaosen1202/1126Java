package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.UserParticipate;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * 用户活动统计：
 *  1. 参与网络会议
 *  2. 查看、编辑协作文档
 *  3. 讨论、通知回帖
 *  4. 提交测验
 *  5. 提交作业
 *  6. 创建wiki page
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER,
        functionName = Constants.FUNCTION_TYPE_PARTICIPATE
)
public class UserParticipateQuery implements ISelfDefinedSearch<List<UserParticipate>> {
    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private AnnounceDao announceDao;

    /**
     * @api {get} /user/participate/query 用户活动查询
     * @apiDescription 用户在课程内活动列表，按时间排序
     * @apiName UserParticipateQuery
     * @apiGroup Grade
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} userId 用户ID
     *
     * @apiSuccess {Number=200,500} code 响应码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} [entity] 活动列表
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {Number} entity.originType 活动对象， 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议
     * @apiSuccess {Number} entity.originId 活动对象ID
     * @apiSuccess {String} entity.targetName 活动对象名称
     * @apiSuccess {Number} entity.operation 活动类型 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）
     * @apiSuccess {Number} entity.createTime 创建时间
     *
     */
    @Override
    public List<UserParticipate> search(Map<String, String> condition) {
        if (!condition.containsKey(USER_ID) || !condition.containsKey(COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get(COURSE_ID));
        long userId = Long.parseLong(condition.get(USER_ID));
        UserParticipate queryRecord = UserParticipate.builder()
                .courseId(courseId)
                .userId(userId)
                .build();
        List<UserParticipate> userParticipates = userParticipateDao.find(queryRecord);

        return userParticipates;
    }

    private static final String USER_ID = "userId";
    private static final String COURSE_ID = "courseId";
}

package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.vo.GradeCommentListVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxutao
 * Des 获取评分评论内容信息
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_Graded_Comment
)
public class GradeCommentDataQuery implements ISelfDefinedSearch<List<GradeCommentListVo>> {
    @Autowired
    private GradeCommentDao gradeCommentDao;

    /**
     * @api {get} /gradeDataQuery/gradedCommentDataQuery/query 获取评分评论内容信息
     * @apiName gradedCommentDataQuery
     * @apiGroup GradeGroup
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验',
     * @apiParam {Number} originId 任务类型ID
     * @apiParam {Number} userId 用户ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {Long} entity.id ID
     * @apiSuccess {Integer} entity.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {Long} entity.originId 来源ID
     * @apiSuccess {String} entity.content 评论内容
     * @apiSuccess {Long} entity.createTime
     * @apiSuccess {Long} entity.userId 评论用户
     * @apiSuccess {String} entity.userName
     * @apiSuccess {String} entity.userFile
     * @apiSuccess {Long} studyGroupId;
     * @apiSuccess {Long} assignmentGroupItemId;
     */
    @Override
    public List<GradeCommentListVo> search(Map<String, String> condition) {
        Long originType =Long.parseLong(condition.get("originType"));
        Long originId = Long.parseLong(condition.get("originId"));
        Long userId = Long.parseLong(condition.get("userId"));
        List<GradeCommentListVo> list = gradeCommentDao.getGradeCommentList(userId, originType, originId);
        return list;
    }
}

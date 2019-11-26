package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.vo.UserSubmissionVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER,
        functionName = Constants.FUNCTION_TYPE_PERCENT_SCORE
)
public class UserPercentScoreQuery implements ISelfDefinedSearch<List<Map<String, Object>>> {
    @Autowired
    private GradeDao gradeDao;


    /**
     * @api {get} /user/percentScore/query 用户任务评分百分比查询
     * @apiDescription 如果originType=1是作业的话，判断isIncludeGrade字段是否计入总分 1:是 0:否
     * 只对计入总分的学生任务得分情况进行统计
     * @apiName UserPercentScore
     * @apiGroup Grade
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} userId 用户ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200请求成功
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} [entity] 得分列表
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.title 任务名称
     * @apiSuccess {Number} entity.score 分数
     * @apiSuccess {Number} entity.gradeScore 得分
     * @apiSuccess {Number} entity.percent 得分比例（整数表示百分比）
     * @apiSuccess {Number=0,1} entity.isIncludeGrade 是否计入总分
     *
     */
    @Override
    public List<Map<String, Object>> search(Map<String, String> condition) {
        if (!condition.containsKey(PARAM_COURSE_ID) || !condition.containsKey(PARAM_USER_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get(PARAM_COURSE_ID));
        long userId = Long.parseLong(condition.get(PARAM_USER_ID));

        //用户每个任务的得分情况比例
        List<Map<String, Object>> userGrade = gradeDao.findUserGrade(courseId, userId);

        return userGrade;
    }

    public static final String PARAM_COURSE_ID = "courseId";
    public static final String PARAM_USER_ID = "userId";

}

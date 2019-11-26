package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SECTION,
        functionName = Constants.FUNCTION_TYPE_PERCENT_SCORE
)
public class SectionPercentScoreQuery implements ISelfDefinedSearch<List<Map<String, Object>>> {

    @Autowired
    private GradeDao gradeDao;
    /**
     * @api {get} /section/percentScore/query 班级任务评分百分比查询
     * @apiDescription 如果originType=1是作业的话，判断isIncludeGrade字段是否计入总分 1:是 0:否
     *               只对计入总分的学生任务得分情况进行统计
     * @apiName SectionPercentScoreQuery
     * @apiGroup Grade
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} sectionId 班级ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200请求成功
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {Object[]} entity 得分列表
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {Number} entity.score 分数
     * @apiSuccess {Number} entity.gradeScore 得分
     * @apiSuccess {Number} entity.percent 得分比例
     * @apiSuccess {Number} entity.userId 用户id
     */

    @Override
    public List<Map<String, Object>> search(Map<String, String> condition){

        Long courseId =Long.parseLong(condition.get("courseId"));
        Long sectionId = Long.parseLong(condition.get("sectionId"));
        List<Map<String, Object>> sectionPercent = gradeDao.sectionGrade(courseId, sectionId);
        return sectionPercent;
    }
}

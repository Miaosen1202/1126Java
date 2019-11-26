package com.wdcloud.lms.business.grade;


import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.vo.GradeInfoVo;
import com.wdcloud.lms.business.grade.vo.GradeQueryVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.SubmissionTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * @author  zhangxutao
 * 批量评分基础信息
 **/
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_STUDY_Grade
)
public class BatchGradeDataQuery implements ISelfDefinedSearch<GradeQueryVo> {
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;

    /**
     * @api {get} /gradeDataQuery/gradeQuery/query 批量、单个评分基础信息
     * @apiName GradeDataQuery
     * @apiGroup Grade2.0
     *
     * @apiParam {Number} courseId 课程ID 【新增】
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiParam {Long} originId 任务类型ID
     * @apiParam {Number} gradeType 单一或批量 1：单一 2：批量
     * @apiParam {String} originName 任务名称
     * @apiParam {String} score 总分值
     * @apiParam {Number} releaseType 个人或小组类型 1:个人 2小组
     * @apiParam {String} [SubmissionType] 作业内容类型 如果是讨论跟测验是没有的
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]}  [entity] 评分的基本信息
     * @apiSuccess {String} entity.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {String} entity.originId  任务类型ID
     * @apiSuccess {String} entity.gradeType  单一或批量 1：单一 2：批量
     * @apiSuccess {String} entity.originName 任务名称
     * @apiSuccess {String} entity.unGGraded 未评分/已评分数量
     * @apiSuccess {Object[]} entity.close 时间列表
     * @apiSuccess {String} entity.close.assignId
     * @apiSuccess {Long} entity.close.assignType
     * @apiSuccess {Long} entity.close.courseId
     * @apiSuccess {Date} entity.close.createTime
     * @apiSuccess {Long} entity.close.createUserId
     * @apiSuccess {Date} entity.close.endTime
     * @apiSuccess {Long} entity.close.id
     * @apiSuccess {Date} entity.close.limitTime
     * @apiSuccess {Long} entity.close.originId
     * @apiSuccess {Integer} entity.close.originType
     * @apiSuccess {Date} entity.close.updateTime
     * @apiSuccess {Long} entity.close.updateUserId
     * @apiSuccess {String} entity.score 总分值
     * @apiSuccess {String} entity.releaseType 个人或小组类型
     * @apiSuccess {String} entity.courseId 课程ID
     * @apiSuccess {String} entity.assignmentGroupItemId 作业组item
     * @apiSuccess {String} entity.submissionType 作业内容类型 如果是讨论跟测验是没有的
     * @apiSuccess {String} entity.showScoreType 评分类型 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分 【新增】
     */
    @Override
    public GradeQueryVo search(Map<String, String> condition) {
        //入参构造
        GradeInfoVo gradeInfoVo = new GradeInfoVo();
        gradeInfoVo.setOriginType(Integer.parseInt(condition.get("originType")));
        gradeInfoVo.setOriginId(Long.parseLong(condition.get("originId")));
        gradeInfoVo.setGradeType(condition.get("gradeType"));
        gradeInfoVo.setOriginName(condition.get("originName"));
        gradeInfoVo.setReleaseType(Integer.parseInt(condition.get("releaseType")));
        gradeInfoVo.setScore(condition.get("score"));
        if(StringUtil.isNotEmpty(condition.get("courseId"))){
            gradeInfoVo.setCourseId(Long.parseLong(condition.get("courseId")));
        }
        gradeInfoVo.setShowScoreType(2);
        //作业
        if (OriginTypeEnum.ASSIGNMENT.getType().equals(gradeInfoVo.getOriginType())) {
            Assignment assignment = assignmentDao.get(gradeInfoVo.getOriginId());
            if (assignment != null) {
                gradeInfoVo.setShowScoreType(assignment.getShowScoreType());
            }
            buildSubmissionType(assignment,gradeInfoVo);
        }
        //设置任务小组内任务项ID
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndTypeId(gradeInfoVo.getOriginId(),gradeInfoVo.getOriginType());
        if (assignmentGroupItem != null) {
            gradeInfoVo.setAssignmentGroupItemId(assignmentGroupItem.getId());
        }
        //设置分配列表
        List<Assign> assignList = assignDao.find( Assign.builder().originId(gradeInfoVo.getOriginId()).originType(gradeInfoVo.getOriginType()).build());
        gradeInfoVo.setClose(assignList);

        return GradeQueryVo.builder().gradeInfoVo(gradeInfoVo).build();

    }

    /**
     * 构建作业提交类型
     * @param assignment
     * @param gradeInfoVo
     */
    private void buildSubmissionType(Assignment assignment, GradeInfoVo gradeInfoVo) {
        if (assignment != null &&
                assignment.getSubmissionType() != null &&
                assignment.getSubmissionType().equals(SubmissionTypeEnum.ONLINE.getType())) {
            StringBuilder stringBuilder = new StringBuilder();
            if (assignment.getAllowText().equals(Status.YES.getStatus())) {
                stringBuilder.append(Submission_TEXT);
            }
            if (assignment.getAllowUrl().equals(Status.YES.getStatus())) {
                stringBuilder.append(Submission_URL);
            }
            if (assignment.getAllowFile().equals(Status.YES.getStatus())) {
                stringBuilder.append(Submission_MEDIA);
            }
            if (assignment.getAllowMedia().equals(Status.YES.getStatus())) {
                stringBuilder.append(Submission_FILE);
            }
            gradeInfoVo.setSubmissionType(stringBuilder);
        }
    }

    public static final String Submission_TEXT = "Text Entry,";
    public static final String Submission_URL = "Website URL,";
    public static final String Submission_MEDIA = "Media,";
    public static final String Submission_FILE = "File Uploads,";

}

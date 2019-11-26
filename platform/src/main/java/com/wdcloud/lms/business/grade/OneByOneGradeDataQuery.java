package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.enums.GradeOverdueEnum;
import com.wdcloud.lms.business.grade.enums.StudyGroupSetEnum;
import com.wdcloud.lms.business.grade.vo.GradeOneBasicsInfoVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.AssignmentReply;
import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.lms.core.base.vo.CosUserSubmitTimeVO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangxutao
 * 批量评分基础信息
 **/

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_OneByOne_GradeInfo
)
public class OneByOneGradeDataQuery implements ISelfDefinedSearch<GradeOneBasicsInfoVo> {
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private QuizRecordDao quizRecordDao;

    /**
     * @api {get} /gradeDataQuery/gradeContentInfo/query 单个评分信息
     * @apiName gradeContentInfo
     * @apiGroup GradeGroup
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验',
     * @apiParam {Number} originId 任务类型ID
     * @apiParam {Number} releaseType 个人或小组类型
     * @apiParam {Number} studyGroupId 小组ID
     * @apiParam {Number} userId 用户ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {String} entity.originState 任务状态 On time / Late / Missing
     * @apiSuccess {String} entity.submittedTime 提交时间
     * @apiSuccess {String} entity.attemptTook 答题用时 只有任务类型是测验的时候显示
     * @apiSuccess {String} entity.submissionToView 作业跟测验，可以提交多次 显示多次提交时间 ；讨论只有一次，不显示【屏蔽】|，|
     */
    @Override
    public GradeOneBasicsInfoVo search(Map<String, String> condition) {
        GradeOneBasicsInfoVo result = new GradeOneBasicsInfoVo();
        Integer originType = Integer.parseInt(condition.get("originType"));
        Long originId = Long.parseLong(condition.get("originId"));
        Long studyGroupId = Long.parseLong(condition.get("studyGroupId"));
        Long userId = Long.parseLong(condition.get("userId"));

        String state = null;
        /**
         * 任务状态说明:看产品文档
         */
        studyGroupId = studyGroupId == StudyGroupSetEnum.Yes.getValue().longValue() ? null : studyGroupId;

        CosUserSubmitTimeVO gradeCosUserSubmitTimeVo = userSubmitRecordDao.
                getCosUserSubmitTime(originType, originId, studyGroupId, userId);
        if (gradeCosUserSubmitTimeVo == null) {
            state = OriginState_Missing;
        } else {
            if (GradeOverdueEnum.Overdue.getValue().equals(gradeCosUserSubmitTimeVo.getIsOverdue())) {
                state = OriginState_Late;
            } else {
                state = OriginState_OnTime;
            }
            result.setSubmittedTime(gradeCosUserSubmitTimeVo.getLastSubmitTime());
        }
        result.setOriginState(state);
        List<Date> listDate = new ArrayList<>();
        if (originType.equals(OriginTypeEnum.QUIZ.getType())) {
            /**
             * 测验算出答题时间、多次提交时间list
             */
            Integer seconds = quizDao.getMinutes(userId, originId);
            result.setAttemptTook(String.valueOf(seconds));
            List<QuizRecord> list = quizRecordDao.find(
                    QuizRecord.builder()
                            .testerId(userId)
                            .quizId(originId)
                            .build()).stream()
                    .sorted(Comparator.comparing(QuizRecord::getSubmitTime).reversed()).collect(Collectors.toList());
            if (ListUtils.isNotEmpty(list)) {
                for (QuizRecord item : list) {
                    listDate.add(item.getSubmitTime());
                }
            }
        }
        if (originType.equals(OriginTypeEnum.ASSIGNMENT.getType())) {
            List<AssignmentReply> assignmentReplyList = assignmentReplyDao.find(
                    AssignmentReply.builder()
                            .assignmentId(originId)
                            .userId(userId)
                            .build()).stream()
                    .sorted(Comparator.comparing(AssignmentReply::getSubmitTime).reversed()).collect(Collectors.toList());
            if (ListUtils.isNotEmpty(assignmentReplyList)) {
                for (AssignmentReply item : assignmentReplyList) {
                    listDate.add(item.getSubmitTime());
                }
            }
        }
        result.setSubmissionToView(listDate);

        return result;
    }

    public static final String OriginState_Missing = "Missing";
    public static final String OriginState_Late = "Late";
    public static final String OriginState_OnTime = "OnTime";
}

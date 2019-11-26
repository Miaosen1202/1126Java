package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.lms.core.base.vo.UserSubmitRecordVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER,
        functionName = Constants.FUNCTION_TYPE_SUBMISSION_SUMMARY
)
public class UserSubmissionSummaryQuery implements ISelfDefinedSearch<List<Map<String, Object>>> {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;

    /**
     * @api {get} /user/submissionSummary/query 用户任务提交数据统计查询
     * @apiDescription 查询学生在课程中任务的提交情况。 返回数据按任务提交记录的时间（lastSubmitTime）进行排序，
     * 如果没提交，按照关闭时间排序,如果没有提交时间也没有关闭时间则不统计
     * <h2>提交状态[按时提交(on time)、迟交(late)、缺交(miss)规则]：</h2><pre>
     *  按是否逾期字段判断是否按时提交，
     *  如果否逾期字段为空，就看当前时间是否过了关闭时间，过了就说明是缺交，没过不统计<br>
     *
     * @apiName UserSubmissionSummaryQuery
     * @apiGroup Grade
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} userId 用户ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200请求成功
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} [entity] 统计列表
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.groupName 任务所属组名称
     * @apiSuccess {String} entity.title 任务名称
     * @apiSuccess {Number} entity.score 分数
     * @apiSuccess {Number} entity.isGraded 是否已评分
     * @apiSuccess {Number} [entity.gradeScore] 得分
     * @apiSuccess {Number} [entity.startTime] 任务开始时间
     * @apiSuccess {Number} [entity.endTime] 任务结束时间
     * @apiSuccess {Number} [entity.limitTime] 任务截止时间
     * @apiSuccess {Number} [entity.submitTime] 任务提交时间
     * @apiSuccess {Number} [entity.createTime] 任务创建时间
     * @apiSuccess {Number=0,1} [entity.isOverdue] 任务提交是否逾期
     *
     */
    @Override
    public List<Map<String, Object>> search(Map<String, String> condition) {
        if (!condition.containsKey(PARAM_COURSE_ID) || !condition.containsKey(PARAM_USER_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get(PARAM_COURSE_ID));
        long userId = Long.parseLong(condition.get(PARAM_USER_ID));
        //查询用户共有多少个任务
        List<Map<String, Object>> userIdTask=assignmentGroupItemDao.findUserIdTask(courseId,userId,Status.YES);

        for (Map<String, Object> map : userIdTask) {
            long originId = (long) map.get("originId");
            Integer originType = (Integer) map.get("originType");
            //如果是小组任务，study_group_id有值，只要一个人提交就算提交
            UserSubmitRecordVo userSubmitRecord=userSubmitRecordDao.findUserSubmitRecord(courseId,originId,originType,userId);
            if(userSubmitRecord!=null){
                map.put("isGraded",userSubmitRecord.getIsGraded());
                map.put("isOverdue",userSubmitRecord.getIsOverdue());
                map.put("submitTime",userSubmitRecord.getCreateTime());
                Grade grade=Grade.builder()
                        .userId(userId)
                        .originType(originType)
                        .originId(originId)
                        .courseId(courseId)
                        .build();
                Grade gradeOne = gradeDao.findOne(grade);
                if(gradeOne!=null){
                    map.put("gradeScore",gradeOne.getGradeScore());
                    map.put("score",gradeOne.getScore());
                }

            }
        }

        /**
         *  按任务提交记录的时间排序，如果没提交，按照关闭时间排序,
         *  如果没有提交时间也没有关闭时间则不统计
         */
        return userIdTask.stream()
                .sorted((a, b) -> {
                    Date aSubmitTime =(Date) a.get("submitTime");
                    Date bSubmitTime = (Date)b.get("submitTime");
                    Date aEndTime = (Date)a.get("endTime");
                    Date bEndTime = (Date)b.get("endTime");
                    int res=0;
                    if(aEndTime!=null && bEndTime==null){
                        return 1;
                    }
                    if(aEndTime==null && bEndTime!=null){
                        return -1;
                    }
                    if(aEndTime!=null && bEndTime!=null){
                        Date aTime = aSubmitTime == null ? aEndTime : aSubmitTime;
                        Date bTime = bSubmitTime == null ? bEndTime : bSubmitTime;
                         res = aTime.compareTo(bTime);
                    }
                    return  res;
                }).collect(Collectors.toList());
    }

    public static final String PARAM_COURSE_ID = "courseId";
    public static final String PARAM_USER_ID = "userId";
}

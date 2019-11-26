package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.lms.core.base.vo.GradeTestListQueryVo;
import com.wdcloud.lms.core.base.vo.UserSubmitRecordVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SECTION,
        functionName = Constants.FUNCTION_TYPE_SUBMISSION_SUMMARY
)
public class SectionSubmissionSummaryQuery implements ISelfDefinedSearch<List<GradeTestListQueryVo>> {
    @Autowired
    private GradeBookQuery gradeBookQuery;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignDao assignDao;
    /**
     * @api {get} /section/submissionSummary/query 班级下的学生任务提交情况统计
     * @apiDescription 查询班级学生在课程中任务的提交情况。返回数据按任务开始的时间进行排序
     * <h2>任务显示时，时间规则：</h2>
     *   <pre>学生任务的开始时间，如果有多个开始时间，取最早的那个时间</pre>
     * <h2>提交状态[按时提交(on time)、迟交(late)、缺交(miss)规则]：</h2><pre>
     *  按是否逾期字段判断是否按时提交，
     *  如果否逾期字段为空，就看当前时间是否过了关闭时间，过了就说明是缺交，没过不统计<br>
     *
     * @apiName sectionSubmissionSummaryQuery
     * @apiGroup Grade
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} sectionId 班级ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200请求成功
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} entity.submitGradeQuery 任务统计列表
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord 每个任务里边分配学生提交的情况
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.id 任务id
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.courseId 课程ID
     * @apiSuccess {String} entity.submitGradeQuery.userSubmitRecord.title 任务名称
     * @apiSuccess {String} entity.submitGradeQuery.userSubmitRecord.userId 用户id
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.originType 来源类型，1: 作业 2:讨论 3:测验  4: 文件 5：页面 6：公告
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.originId 来源ID
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.startTime 任务开始时间
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.endTime 任务结束时间
     * @apiSuccess {Number} entity.submitGradeQuery.userSubmitRecord.limitTime 任务截止时间
     * @apiSuccess {Number=0,1} entity.isOverdue 任务提交是否逾期
     *
     */
    @Override
    public List<GradeTestListQueryVo> search(Map<String, String> condition) {
        Long courseId = Long.parseLong(condition.get("courseId"));
        Long sectionId = Long.parseLong(condition.get("sectionId"));
        //查询班级下有多少个学生
        List<SectionUser> sectionUserList = sectionUserDao.find(SectionUser.builder().
                courseId(courseId)
                .sectionId(sectionId)
                .registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                .roleId(RoleEnum.STUDENT.getType())
                .build());
        //得到班级下所有学生的UserId
        Set<Long> sectionUserIdSet = sectionUserList.stream().map(SectionUser::getUserId).collect(Collectors.toSet());
        //查询班级下的所有用户共有多少个任务
        List<GradeTestListQueryVo> userIdTask=gradeDao.gradeTestListQuery(sectionId,courseId,Status.YES);
        for (GradeTestListQueryVo vo : userIdTask) {
            long originId = vo.getOriginId();
            Integer originType = vo.getOriginType();
            //查询这个任务分配的学生
            List<AssignUser> assignUserList=assignDao.findTask(originId,originType,courseId);
            Map<Long, AssignUser> assignUserMap = assignUserList.stream().collect(Collectors.toMap(AssignUser::getUserId, Function.identity(),
                    (o,n) ->o));
            //当前任务分配的所有学生的userId
            Set<Long> assignStudentIds=assignUserList.stream().map(AssignUser::getUserId).collect(Collectors.toSet());
            Set<Long> stuIds = new HashSet<>();
            stuIds.addAll(sectionUserIdSet);
            //取出当前任务分配的所有学生跟班级下所有学生的交集
            stuIds.retainAll(assignStudentIds);
            List<UserSubmitRecord> submitGradeQuery=new ArrayList<UserSubmitRecord>();

            //批量获取学生任务的提交情况 1:普通任务 2:小组集任务
            if(!stuIds.isEmpty()){
             //根据交集查询这个任务的提交情况
            List<UserSubmitRecordVo> submitRecordVoList=userSubmitRecordDao.findUserSubmitRecordByStuIds(courseId,originId,originType,stuIds);
            Map<Long, UserSubmitRecordVo> submitRecordVoListMap = submitRecordVoList.stream()
                    .collect(Collectors.toMap(UserSubmitRecordVo::getUserId, o ->o));
            //小组集任务数据构造
            if(vo.getStudyGroupSetId()!=null){
                submitRecordVoListMap =gradeBookQuery.buildGroupStuData(vo,submitRecordVoList);
            }
            for (Long userId: stuIds) {
                    //如果是小组任务，study_group_id有值，只要一个人提交就算提交
                    UserSubmitRecordVo userSubmitRecord = submitRecordVoListMap.get(userId);
                    if(userSubmitRecord==null){
                        userSubmitRecord=new UserSubmitRecordVo();
                    }
                    try {
                        //查询当前任务的分配信息
                        AssignUser assignUser = assignUserMap.get(userId);
                        userSubmitRecord.setAssignUserId(assignUser.getId());
                        userSubmitRecord.setUserId(userId);
                        userSubmitRecord.setEndTime(assignUser.getEndTime());
                    }catch (Exception e){
                        log.error("originId,originType,userId"+originId+":::"+originType+":::"+userId);
                    }
                    submitGradeQuery.add(userSubmitRecord);
               }
            }
            vo.setSubmitGradeQuery(submitGradeQuery);
        }
         return userIdTask;
    }
}

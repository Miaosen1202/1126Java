package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.GradeTestListQueryVo;
import com.wdcloud.lms.core.base.vo.UserSubmitRecordVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_GREDE,
        functionName = Constants.FUNCTION_TYPE_BOOK
)
public class GradeBookQuery implements ISelfDefinedSearch<Map<String, Object>> {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private  QuizDao quizDao;
    @Autowired
    private  StudyGroupUserDao studyGroupUserDao;

    /**
     *
     * @api {get} /grade/book/query 成绩册列表GradeBook
     * @apiDescription 判断任务的提交状态：如果is_graded，is_overdue为空，用当前时间与测验结束日期比较，
     * 如果过了结束日期是缺交状态，如果没过结束日期，是未提交状态，is_graded判断是否评分，is_overdue是否逾期提交
     * 如果结束时间为空，有没有提交，状态展示为未提交状态
     * 不计入总分类型：测验：如果type=1则不计入总分
     *              作业：isIncludeGrade=0则不计入总分
     * @apiName gradeBookQuery
     * @apiGroup Grade2.0
     *
     * @apiParam {String} courseId 课程id
     * @apiParam {String} sectionId 班级id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {Object[]} entity.userList 班级下的学生列表
     * @apiSuccess {Long} entity.userList.id 学生id
     * @apiSuccess {String} entity.userList.fullName 用户全称
     * @apiSuccess {String} entity.userList.email 邮箱
     * @apiSuccess {String} entity.userList.fileUrl 学生头像
     * @apiSuccess {Object[]} entity.gradeTestListQuery 分配给班级下的任务以及班级下所有学生的任务列表
     * @apiSuccess {Number} entity.gradeTestListQuery.id 任务id
     * @apiSuccess {Number} entity.gradeTestListQuery.originId 来源id
     * @apiSuccess {Number} entity.gradeTestListQuery.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {String} entity.gradeTestListQuery.title 任务名
     * @apiSuccess {Number} entity.gradeTestListQuery.assignmentGroupId 任务组id
     * @apiSuccess {String} entity.gradeTestListQuery.name 任务组名
     * @apiSuccess {String} entity.gradeTestListQuery.isIncludeGrade 作业判断是否包含成绩到总分
     * @apiSuccess {String} entity.gradeTestListQuery.showScoreType 作业评分方式 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
     * @apiSuccess {Number} entity.gradeTestListQuery.score 任务总分
     * @apiSuccess {String} entity.gradeTestListQuery.isGradeAssignment 属于小组还是个人
     * @apiSuccess {Object} entity.gradeTestListQuery.quizOne 测验信息
     * @apiSuccess {Number} entity.gradeTestListQuery.quizOne.type 测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)
     * @apiSuccess {Object[]} entity.gradeTestListQuery.submitGradeQuery 学生的得分情况列表
     * @apiSuccess {Long} entity.gradeTestListQuery.submitGradeQuery.submitQuery.userId 用户id
     * @apiSuccess {Number} entity.gradeTestListQuery.submitGradeQuery.submitQuery.gradeScore 用户当前任务得分
     * @apiSuccess {Number} entity.gradeTestListQuery.submitGradeQuery.submitQuery.isGraded 是否已评分  0：否 1：是
     * @apiSuccess {Number} entity.gradeTestListQuery.submitGradeQuery.submitQuery.isOverdue 是否逾期提交
     * @apiSuccess {Date} entity.gradeTestListQuery.submitGradeQuery.submitQuery.lastSubmitTime 可以参加测验结束日期
     * @apiSuccess {Object[]} entity.gradeTestListQuery.assignsList 任务分配的具体信息列表
     * @apiSuccess {Long} entity.gradeTestListQuery.assignsList.assignType 任务类型
     * @apiSuccess {Date} entity.gradeTestListQuery.assignsList.limitTime 规定截止日期
     * @apiSuccess {Date} entity.gradeTestListQuery.assignsList.startTime 可以参加测验开始日期
     * @apiSuccess {Date} entity.gradeTestListQuery.assignsList.endTime 可以参加测验结束日期
     */
    @Override
    public Map<String, Object> search(Map<String, String> condition)
    {
        Long sectionId = Long.parseLong(condition.get("sectionId"));
        Long courseId = Long.parseLong(condition.get("courseId"));

        //班级下的学生基本信息
        List<Map<String, Object>> userList=gradeDao.userQuery(sectionId,courseId);
        //当前班级下所有学生的userId
        Set<Long> sectionUserIdSet = userList.stream().map(a -> (Long)a.get("id")).collect(Collectors.toSet());
        //查询班级下的学生共有多少个任务
        List<GradeTestListQueryVo> gradeTestListQuery=gradeDao.gradeTestListQuery(sectionId,courseId, Status.YES);

        if(ListUtils.isNotEmpty(userList)&& ListUtils.isNotEmpty(gradeTestListQuery)){
        /**
         * ************优化***start********************
         */
         Map<Long, GradeTestListQueryVo> assignmentMap = (Map<Long, GradeTestListQueryVo>) buildAssignmentMap(gradeTestListQuery,OriginTypeEnum.ASSIGNMENT.getType());
         Map<Long, GradeTestListQueryVo> discussionMap = (Map<Long, GradeTestListQueryVo>) buildAssignmentMap(gradeTestListQuery, OriginTypeEnum.DISCUSSION.getType());
         Map<Long, Quiz> quizMap = (Map<Long, Quiz>) buildAssignmentMap(gradeTestListQuery, OriginTypeEnum.QUIZ.getType());
        /**
         * ************优化***end*************************
         */
        for (GradeTestListQueryVo gradeTestListQueryVo : gradeTestListQuery) {
           Long originId=gradeTestListQueryVo.getOriginId();
           Integer originType=gradeTestListQueryVo.getOriginType();
            //任务属于个人还是小组
            if(OriginTypeEnum.ASSIGNMENT.getType().equals(originType)){
                gradeTestListQueryVo.setIsGradeAssignment(assignmentMap.get(originId).getIsGradeAssignment());
                gradeTestListQueryVo.setStudyGroupSetId(assignmentMap.get(originId).getStudyGroupSetId());
            }else if(OriginTypeEnum.DISCUSSION.getType().equals(originType)){
                gradeTestListQueryVo.setIsGradeAssignment(discussionMap.get(originId).getIsGradeAssignment());
                gradeTestListQueryVo.setStudyGroupSetId(discussionMap.get(originId).getStudyGroupSetId());
            } else if(OriginTypeEnum.QUIZ.getType().equals(originType)){
                //查询测验：测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)，如果是练习测验，则不计入总分
                gradeTestListQueryVo.setQuizOne(quizMap.get(originId));
            }
            List<UserSubmitRecord> submitGradeQuery=new ArrayList<UserSubmitRecord>();
            //查询这个任务分配的学生
            List<AssignUser> assignUserList=assignDao.findTask(originId,originType,courseId);
            Map<Long, AssignUser> assignUserMap = assignUserList.stream().collect(Collectors.toMap(AssignUser::getUserId, Function.identity(),
                    (o,n) ->o));
            //得到当前任务分配的学生的userId
            Set<Long> assignStudentIds=assignUserList.stream().map(AssignUser::getUserId).collect(Collectors.toSet());
            //取出当前任务分配的所有学生跟班级下所有学生的交集
            Set<Long> stuIds = new HashSet<>();
            stuIds.addAll(sectionUserIdSet);
            stuIds.retainAll(assignStudentIds);
            //批量获取学生任务的提交情况 1:普通任务 2:小组集任务
            if(!stuIds.isEmpty()){
             //根据交集查询这个任务的提交情况
            List<UserSubmitRecordVo> submitRecordVoList=userSubmitRecordDao.findUserSubmitRecordByStuIds(courseId,originId,originType,stuIds);
            Map<Long, UserSubmitRecordVo> submitRecordVoListMap = submitRecordVoList.stream()
                    .collect(Collectors.toMap(UserSubmitRecordVo::getUserId, o ->o));

            //小组集任务数据构造
            if(gradeTestListQueryVo.getStudyGroupSetId()!=null){
                submitRecordVoListMap =buildGroupStuData(gradeTestListQueryVo,submitRecordVoList);
            }
            //获取当前任务的得分情况
            List<Grade> gradeAll = gradeDao.find(Grade.builder()
                    .originType(originType)
                    .originId(originId)
                    .courseId(courseId)
                    .build());
            Map<Long, Grade> gradeMap = gradeAll.stream()
                    .collect(Collectors.toMap(Grade::getUserId,Function.identity(), (o,n) ->o));
            for (Long stuId: stuIds) {
                //任务提交情况
                UserSubmitRecordVo submitQuery = submitRecordVoListMap.get(stuId);
                 if(submitQuery==null){
                      submitQuery=new UserSubmitRecordVo();
                 }else{
                     Grade gradeOne = gradeMap.get(stuId);
                     if(gradeOne!=null){
                         //任务的得分
                         submitQuery.setGradeScore(gradeOne.getGradeScore());
                     }
                 }
                try {
                    //任务的分配信息
                    AssignUser assignUser = assignUserMap.get(stuId);
                    submitQuery.setAssignUserId(assignUser.getId());
                    submitQuery.setUserId(stuId);
                    submitQuery.setEndTime(assignUser.getEndTime());
                }catch (Exception e){
                    log.error("originId,originType,userId"+originId+":::"+originType+":::"+stuId);
                }
                submitGradeQuery.add(submitQuery);
            }
            gradeTestListQueryVo.setSubmitGradeQuery(submitGradeQuery);
        }
        }
        }
        return Map.of("userList", userList,"gradeTestListQuery",gradeTestListQuery);
    }

    /**
     * 构造任务Map
     * 通过type+ID 批量获取 作业|讨论是否是小组集
     * @param gradeTestListQuery
     * @param originType
     * @return
     */
    private Map<Long,  ? extends Object> buildAssignmentMap(List<GradeTestListQueryVo> gradeTestListQuery, Integer originType) {
        List<Long> assignmentIdList = gradeTestListQuery.stream()
                .filter(o ->originType.equals( o.getOriginType()))
                .map(GradeTestListQueryVo::getOriginId).collect(Collectors.toList());

        if (ListUtils.isNotEmpty(assignmentIdList)) {
            if (originType.equals(OriginTypeEnum.ASSIGNMENT.getType())) {
                //判断是否是小组作业
              return gradeDao.ext().isgGroupAssignmentBatch(assignmentIdList).stream()
                       .collect(Collectors.toMap(GradeTestListQueryVo::getOriginId, o ->o));
            }else if(originType.equals(OriginTypeEnum.DISCUSSION.getType())) {
                //判断是否是小组讨论
                return gradeDao.ext().isgGroupDiscussionBatch(assignmentIdList).stream()
                        .collect(Collectors.toMap(GradeTestListQueryVo::getOriginId, o ->o));
            }else if(originType.equals(OriginTypeEnum.QUIZ.getType())) {
               return quizDao.gets(assignmentIdList).stream().collect(Collectors.toMap(Quiz::getId, o ->o));
            }
        }
        return null;
    }

    public Map<Long, UserSubmitRecordVo> buildGroupStuData(GradeTestListQueryVo gradeTestListQueryVo, List<UserSubmitRecordVo> submitRecordVoList) {
        //获取小组Id:学生IDs关系
        List<StudyGroupUser>  studyGroupUserList=studyGroupUserDao.findGroupSetUsers(List.of(gradeTestListQueryVo.getStudyGroupSetId()));
        Map<Long, List<Long>> studyGroupUserMap = studyGroupUserList.stream()
                .collect(Collectors.groupingBy(StudyGroupUser::getStudyGroupId,
                        Collectors.mapping(StudyGroupUser::getUserId, Collectors.toList())));
        //同组学生有一人提交，复制副本
        List<UserSubmitRecordVo> submitRecordVoList1 = new ArrayList<>();
        Set<Long> groupIds = new HashSet<>();
        submitRecordVoList.forEach(userSubmitRecordVo -> {
            if (userSubmitRecordVo.getStudyGroupId()!=null) {
                if(!groupIds.contains(userSubmitRecordVo.getStudyGroupId())){
                    groupIds.add(userSubmitRecordVo.getStudyGroupId());
                    List<Long> groupStuIds = studyGroupUserMap.get(userSubmitRecordVo.getStudyGroupId());
                    groupStuIds.forEach(stuId->{
                        UserSubmitRecordVo vo = BeanUtil.beanCopyProperties(userSubmitRecordVo,UserSubmitRecordVo.class);
                        vo.setUserId(stuId);
                        submitRecordVoList1.add(vo);
                    });
                }
            }else{
                submitRecordVoList1.add(userSubmitRecordVo);
            }
        });
        return  submitRecordVoList1.stream()
                .collect(Collectors.toMap(UserSubmitRecordVo::getUserId,Function.identity(), (o,n) ->o));
    }
}

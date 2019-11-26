package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.enums.GradedStatusEnum;
import com.wdcloud.lms.business.grade.enums.ReleaseTypeEnum;
import com.wdcloud.lms.business.grade.enums.StudyGroupSetEnum;
import com.wdcloud.lms.business.grade.enums.UserTypeEnum;
import com.wdcloud.lms.business.grade.vo.BatchGradeInfoVo;
import com.wdcloud.lms.business.grade.vo.OriginInfoVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.GradedSummeryDataVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;


/**
 * @author zhangxutao
 * 批量评分统计信息
 **/
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_Statistics_Graded
)
public class BatchGradedDataInfoQuery implements ISelfDefinedSearch<BatchGradeInfoVo> {
    @Autowired
    private StudyGroupDao studyGroupDao;

    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private OriginInfoService originInfoService;


    /**
     * @api {get} /gradeDataQuery/gradeStatisticsQuery/query 批量评分统计信息
     * @apiName gradeStatisticsQuery
     * @apiGroup GradeGroup
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验',
     * @apiParam {Number} originId 任务类型ID
     * @apiParam {Number} releaseType 个人或小组类型
     * @apiParam {Number} isGrade  是否评分 0：未评分 1：已评分 2：全部
     * @apiParam {Number} studyGroupId  小组ID
     * @apiParam {Number} isGroupAndAll 个人任务和小组任务的All：0 其他传1
     * @apiParam {Number} userId  如果是小组就传0 如果是自然人就传ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 批量、单个评统计信息
     * @apiSuccess {Integer} entity.group 有多少组
     * @apiSuccess {Integer} entity.students 有多少学生
     * @apiSuccess {Integer} entity.unG 未评分
     * @apiSuccess {Integer} entity.graded  已评分
     * @apiSuccess {Integer} entity.unSub 未提交
     * @apiSuccess {Integer} entity.submitted 已提交
     * @apiSuccess {Number} entity.pointsPossible 满分分值
     * @apiSuccess {Number} entity.averageScore 平均分
     * @apiSuccess {Number} entity.highScore 最高分
     * @apiSuccess {Number} entity.lowScore 最低分
     * @apiSuccess {Number} entity.totalStu 统计学生数量
     */
    @Override
    public BatchGradeInfoVo search(Map<String, String> condition) {
        BatchGradeInfoVo batchGradeInfoVo = new BatchGradeInfoVo();
        /**
         * 前端传参
         */
        Integer originType = Integer.parseInt(condition.get("originType"));
        Long originId = Long.parseLong(condition.get("originId"));
        Long studyGroupId = Long.parseLong(condition.get("studyGroupId"));
        Integer releaseType = Integer.parseInt(condition.get("releaseType"));
        Integer isGrade = Integer.parseInt(condition.get("isGrade"));
        /**
         * 个人任务和小组任务的All：0 其他传1
         */
        Integer isGroupAndAll = Integer.parseInt(condition.get("isGroupAndAll"));
        /**
         * 如果是小组就传0 如果是自然人就传ID
         */
        Long userId = Long.parseLong(condition.get("userId"));
        /**
         * 获取任务小组集ID、课程ID
         */
        OriginInfoVo model = originInfoService.getOriginInfo(originId, originType);

        /**
         * 计算批量评分界面中的：提交人数、已评分人员、小组个数、未评分人数
         * 1、选择全部评分【包括评分、未评分】、评分、未评分
         * 2、选择全部小组【全部小组】、单个小组
         */
        Integer gradeCount = getCalGradedNum(batchGradeInfoVo, originType, originId,
                model.getCourseId(), releaseType, isGrade, isGroupAndAll,
                studyGroupId, model.getStudyGroupSetId(), userId);
        /**
         * 评分统计
         * 特殊处理字段：studyGroupId、isGrade、userId
         */
        if (isGroupAndAll.equals(StudyGroupSetEnum.Yes.getValue()) ||
                userId.equals(Long.parseLong(UserTypeEnum.No.getValue().toString())) ||
                userId.equals((Long.parseLong(UserTypeEnum.All.getValue().toString())))) {
            studyGroupId = null;
        }
        if (isGrade.equals(GradedStatusEnum.AllGrade.getValue())) {
            isGrade = null;
        }
        if (userId.equals(Long.parseLong(UserTypeEnum.Yes.getValue().toString()))) {
            userId = null;
        }
        getGradedSummeryData(batchGradeInfoVo, originType, originId, studyGroupId, isGrade, model.getCourseId(),
                userId, gradeCount);
        return batchGradeInfoVo;
    }

    /**
     * 获取批量评分GradedSummery 信息
     *
     * @param batchGradeInfoVo 返回值集合
     * @param originType       任务类型
     * @param originId         任务ID
     * @param studyGroupId     小组ID
     * @param isGrade          是否评分
     * @param courseId         课程ID
     * @param userId           用户ID
     * @param gradeCount       评分人总数
     */
    private void getGradedSummeryData(BatchGradeInfoVo batchGradeInfoVo, Integer originType, Long originId,
                                      Long studyGroupId, Integer isGrade, Long courseId,
                                      Long userId, Integer gradeCount) {
        GradedSummeryDataVo gradedSummeryDataVo = gradeCommentDao.getGradedSummeryData(
                originType, originId, studyGroupId, isGrade, courseId, userId);
        if (gradedSummeryDataVo != null && gradedSummeryDataVo.getAverageScore() != null) {
            batchGradeInfoVo.setPointsPossible(gradedSummeryDataVo.getPointsPossible());
            batchGradeInfoVo.setAverageScore(gradedSummeryDataVo.getAverageScore());
            batchGradeInfoVo.setHighScore(gradedSummeryDataVo.getHighScore());
            batchGradeInfoVo.setLowScore(gradedSummeryDataVo.getLowScore());
            batchGradeInfoVo.setTotalStu(gradeCount.toString());
        }
    }

    /**
     * 计算批量评分界面数据：学生人数、提交人数、未评分人数、已提交人数
     *
     * @param batchGradeInfoVo
     * @param originType
     * @param originId
     * @param courseId
     * @param releaseType
     * @param isGrade
     * @param isGroupAndAll
     * @param studyGroupId
     * @param studyGroupSetId
     * @param userId
     */
    private Integer getCalGradedNum(BatchGradeInfoVo batchGradeInfoVo, Integer originType, Long originId,
                                    Long courseId, Integer releaseType, Integer isGrade, Integer isGroupAndAll,
                                    Long studyGroupId, Long studyGroupSetId, Long userId) {
        //未评分总人数
        Integer unGradedCount = 0;
        //提交人数
        Integer userSubmitCount = 0;
        Integer total = 0;
        //评分总人数
        Integer gradeCount = 0;
        //查询课程下的所有提交作业的学生List
        List<UserSubmitRecord> userSubmitRecord = userSubmitRecordDao.find(UserSubmitRecord.builder()
                .originType(originType)
                .originId(originId)
                .courseId(courseId)
                .build());
        total = userSubmitRecord.size();
        //查询已评分的人数
        Integer gradeList = gradeDao.getGradeList(originId, originType, courseId);
        if (ReleaseTypeEnum.USER.getValue().equals(releaseType)) {
            /**
             * 获取个人的数据
             */
            gradeCount = getUserCalGradedNum(batchGradeInfoVo, isGrade, total,
                    userSubmitCount, unGradedCount, gradeCount,
                    userSubmitRecord, gradeList);
        } else if (ReleaseTypeEnum.GROUP.getValue().equals(releaseType)) {
            /**
             * 获取组内提交人数
             */
            Integer orgSubmitCount = getOrgSubmitCount(originType, originId, courseId);
            Integer gradeGroupCount = 0;
            Integer groupNum = 0;
            /**
             * 查询单个组内学生人数，那么gList.size()=1;
             */
            if (isGroupAndAll != 0) {
                groupNum = 1;
                List<Map<String, Object>> gradeCommentList = gradeCommentDao.getGradeGroupCount(
                        courseId, originId, originType, studyGroupId);
                gradeGroupCount = gradeCommentList.size();

            } else {
                groupNum = studyGroupDao.findStudyGroupList(studyGroupSetId).size();
                gradeGroupCount = gradeList;
            }
            Integer submitCount = 1;
            List<StudyGroupUser> studyGroupUserList = null;
            if (userId.equals(Long.parseLong(UserTypeEnum.Yes.getValue().toString()))) {
                studyGroupUserList = studyGroupUserDao.find(
                        StudyGroupUser.builder()
                                .studyGroupId(studyGroupId)
                                .build());
            }
            if (!userId.equals(Long.parseLong(UserTypeEnum.Yes.getValue().toString()))) {
                gradeGroupCount = gradeDao.find(Grade.builder()
                        .originId(originId)
                        .originType(originType)
                        .courseId(courseId)
                        .userId(userId)
                        .build()).size();
            }

            submitCount = studyGroupUserList == null ? submitCount : studyGroupUserList.size();

            /**
             * 获取组内数据
             */
            gradeCount = getGroupCalGradedNum(batchGradeInfoVo, groupNum, isGrade,
                    isGroupAndAll, total, orgSubmitCount,
                    userSubmitCount, unGradedCount, gradeList,
                    gradeCount, submitCount, gradeGroupCount,
                    studyGroupUserList);
        }
        return gradeCount;
    }

    /**
     * 获取个人的数据信息
     *
     * @param batchGradeInfoVo
     * @param isGrade
     * @param total
     * @param userSubmitCount
     * @param unGradedCount
     * @param gradeCount
     * @param userSubmitRecord
     * @param gradeList
     * @return gradeCount
     */
    private Integer getUserCalGradedNum(BatchGradeInfoVo batchGradeInfoVo, Integer isGrade, Integer total,
                                        Integer userSubmitCount, Integer unGradedCount, Integer gradeCount,
                                        List<UserSubmitRecord> userSubmitRecord, Integer gradeList) {
        batchGradeInfoVo.setGroup(0);
        batchGradeInfoVo.setUnSub(0);
        if (isGrade.equals(GradedStatusEnum.AllGrade.getValue())) {
            total = userSubmitRecord.size();
            userSubmitCount = userSubmitRecord.size();
            unGradedCount = userSubmitRecord.size() - gradeList;
            gradeCount = gradeList;
        }
        if (isGrade.equals(GradedStatusEnum.IsGrade.getValue())) {
            total = userSubmitRecord.size() - gradeList;
            userSubmitCount = gradeCount + (userSubmitRecord.size() - gradeList);
            unGradedCount = userSubmitRecord.size() - gradeList;
            gradeCount = 0;
        }
        if (isGrade.equals(GradedStatusEnum.Grade.getValue())) {
            total = gradeList;
            userSubmitCount = gradeList;
            unGradedCount = 0;
            gradeCount = gradeList;
        }
        batchGradeInfoVo.setStudents(total);
        batchGradeInfoVo.setSubmitted(userSubmitCount);
        batchGradeInfoVo.setUnG(unGradedCount);
        batchGradeInfoVo.setGraded(gradeCount);

        return gradeCount;
    }

    /**
     * 获取组的数据信息
     *
     * @param batchGradeInfoVo
     * @param groupNum
     * @param isGrade
     * @param isGroupAndAll
     * @param total
     * @param orgSubmitCount
     * @param userSubmitCount
     * @param unGradedCount
     * @param gradeList
     * @param gradeCount
     * @param submitCount
     * @param gradeGroupCount
     * @param studyGroupUserList
     * @return gradeCount
     */
    private Integer getGroupCalGradedNum(BatchGradeInfoVo batchGradeInfoVo, Integer groupNum, Integer isGrade,
                                         Integer isGroupAndAll, Integer total, Integer orgSubmitCount,
                                         Integer userSubmitCount, Integer unGradedCount, Integer gradeList,
                                         Integer gradeCount, Integer submitCount, Integer gradeGroupCount,
                                         List<StudyGroupUser> studyGroupUserList) {
        batchGradeInfoVo.setGroup(groupNum);
        batchGradeInfoVo.setUnSub(0);
        if (isGrade.equals(GradedStatusEnum.AllGrade.getValue())) {
            if (isGroupAndAll.equals(StudyGroupSetEnum.Yes.getValue())) {
                total = orgSubmitCount;
                userSubmitCount = orgSubmitCount;
                unGradedCount = orgSubmitCount - gradeList;
                gradeCount = gradeList;
            } else {
                total = submitCount;
                userSubmitCount = submitCount;
                if (gradeGroupCount > 0) {
                    unGradedCount = gradeGroupCount - submitCount;
                } else {
                    unGradedCount = submitCount;
                }
                gradeCount = gradeGroupCount;
            }
        }
        if (isGrade.equals(GradedStatusEnum.IsGrade.getValue())) {
            if (isGroupAndAll.equals(StudyGroupSetEnum.Yes.getValue())) {
                total = orgSubmitCount - gradeList;
                userSubmitCount = orgSubmitCount - gradeList;
                unGradedCount = orgSubmitCount - gradeList;
                gradeCount = 0;
            } else {
                if (gradeGroupCount > 0) {
                    total = gradeGroupCount - studyGroupUserList.size();
                    userSubmitCount = gradeGroupCount - studyGroupUserList.size();
                    unGradedCount = gradeGroupCount - studyGroupUserList.size();
                    gradeCount = 0;
                } else {
                    total = studyGroupUserList.size();
                    userSubmitCount = studyGroupUserList.size();
                    unGradedCount = studyGroupUserList.size();
                    gradeCount = 0;
                }
            }
        }
        if (isGrade.equals(GradedStatusEnum.Grade.getValue())) {
            if (isGroupAndAll.equals(StudyGroupSetEnum.Yes.getValue())) {
                total = gradeList;
                userSubmitCount = gradeList;
                unGradedCount = 0;
                gradeCount = gradeList;
            } else {
                if (gradeGroupCount > 0) {
                    total = gradeGroupCount;
                    userSubmitCount = gradeGroupCount;
                    unGradedCount = 0;
                    gradeCount = gradeGroupCount;
                } else {
                    total = gradeGroupCount;
                    userSubmitCount = gradeGroupCount;
                    unGradedCount = 0;
                    gradeCount = gradeGroupCount;
                }

            }
        }
        batchGradeInfoVo.setStudents(total);
        batchGradeInfoVo.setSubmitted(userSubmitCount);
        batchGradeInfoVo.setUnG(unGradedCount);
        batchGradeInfoVo.setGraded(gradeCount);
        return gradeCount;
    }

    /**
     * 获取组内提交人数
     *
     * @param originType 任务类型
     * @param originId   任务ID
     * @param courseId   课程ID
     * @return orgSubmitCount
     */
    private Integer getOrgSubmitCount(Integer originType, Long originId, Long courseId) {
        Integer orgSubmitCount = 0;
        List<Map<String, Object>> getSubmitStudyGroupDistinct = userSubmitRecordDao.getSubmitStudyGroupDistinct(originType, originId, courseId);
        if (getSubmitStudyGroupDistinct.size() > 0) {
            for (Map<String, Object> item : getSubmitStudyGroupDistinct) {
                if (item != null && item.get("study_group_id").toString() != null) {
                    List<StudyGroupUser> studyGroupUserList = studyGroupUserDao.find(
                            StudyGroupUser.builder()
                                    .studyGroupId(Long.parseLong(item.get("study_group_id").toString()))
                                    .build());
                    orgSubmitCount += studyGroupUserList.size();
                } else {
                    orgSubmitCount += 1;
                }
            }
        }
        return orgSubmitCount;

    }

}

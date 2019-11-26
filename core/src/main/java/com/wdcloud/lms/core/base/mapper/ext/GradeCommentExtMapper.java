package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.vo.*;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface GradeCommentExtMapper {

    List<Map<String, Object>> userQuery(@Param("sectionId") Long sectionId, @Param("courseId") Long courseId);

    List<GradeTestListQueryVo> gradeTestListQuery(@Param("sectionId") Long sectionId,
                                                  @Param("courseId") Long courseId,
                                                  @Param("status") @Nullable Integer status);
    List<GradeCommentVO> findAssignmentComment(Map<String, Object> map);

    /**
     * 查询在当前课程下需要评分的任务并且已经过了发布时间
     * @param originType,type
     * @return
     */
    List<GradeTaskVO> gradeTestQuery(@Param("originType")String originType, @Param("courseId")Long courseId);

    /**
     * 获取评分统计数据
     * @param originType 任务类型
     * @param originId 任务ID
     * @param studyGroupId 小组ID
     * @param isGraded 是否评分
     * @param courseId 课程ID
     * @param userId 用户ID
     * @return GradedSummeryDataVo
     */
    GradedSummeryDataVo getGradedSummeryData(@Param("originType") Integer originType,
                                             @Param("originId") Long originId,
                                             @Param("studyGroupId") Long studyGroupId,
                                             @Param("isGraded") Integer isGraded,
                                             @Param("courseId") Long courseId,
                                             @Param("userId") Long userId);

    /**
     * 小组内评分人员总数
     * @param courseId 课程ID
     * @param originId 任务ID
     * @param originType 任务类型
     * @param studyGroupId 小组ID
     * @return List<Map<String,Object>>
     */
     List<Map<String,Object>> getGradeGroupCount(@Param("courseId") Long courseId,
                                                 @Param("originId") Long originId,
                                                 @Param("originType") Integer originType,
                                                 @Param("studyGroupId") Long studyGroupId);



    /*
     * 获取用户得分比例高的前10的任务
     * @param userId
     * @return
     * */
    List<Map<String, Object>> taskToTheEnd(@Param("userId") Long userId, @Param("courseId") Long courseId);

    /*
     * 获取用户得分比例低的前10的任务
     * @param userId
     * @return
     * */
    List<Map<String, Object>> taskLowToHigh(@Param("userId") Long userId, @Param("courseId") Long courseId);

    /**
     * 获取评论信息
     * @param userId 用户ID
     * @param originType 任务类型
     * @param originId 任务ID
     * @return
     * */
    List<GradeCommentListVo> getGradeCommentList(@Param("userId") Long userId,
                                                 @Param("originType") Long originType,
                                                 @Param("originId") Long originId);


    /**
     * 获取评分后的数据
     * @param originType 任务类型
     * @param originId 任务ID
     * @param userId 用户ID
     * @param studyGroupId 小组ID
     * @param courseId 课程ID
     * @return CosGradeVo
     */
    CosGradeVo getCosGradeInfo(@Param("originType") Integer originType,
                                              @Param("originId") Long originId,
                                              @Param("userId") Long userId,
                                              @Param("studyGroupId") Long studyGroupId,
                                              @Param("courseId") Long courseId);

    /**
     * 获取已评分的用户信息——List
     */
    List<Map<String, Object>> getUserSubmitGradeList(@Param("originType") Integer originType,
                                                     @Param("originId") Long originId,
                                                     @Param("assignmentGroupItemId") Long assignmentGroupItemId);

    /**
     * 获取我的评分信息
     * @param userId 用户ID
     * @param courseId 课程ID
     * @return List<MyGradeBookListVo>
     */
    List<MyGradeBookListVo> getMyGradeBookList(@Param("userId") Long userId,
                                               @Param("courseId") Long courseId);

    /*
     *作业任务是属于个人还是小组
     * @param originId,originType
     * @return
     * */
    String isgGroupAssignment(@Param("originId") Long originId, @Param("originType") Integer originType);

    /*
     *讨论任务是属于个人还是小组
     * @param originId,originType
     * @return
     * */
    String isGroupDiscussion(@Param("originId") Long originId, @Param("originType") Integer originType);

    /**
     * 班级下用户的得分情况比例
     *
     * @param courseId
     * @param sectionId
     * @return
     */
    List<Map<String, Object>> sectionGrade(@Param("courseId") Long courseId, @Param("sectionId") Long sectionId);

    /**
     * 任务分配给每个用户，用户的提交及得分情况
     *
     * @param originId
     * @param originType
     * @return
     */
    Map<String,Object> submitGradeQuery(@Param("originId") Long originId,
                                               @Param("originType") Integer originType,
                                               @Param("userId")Long userId,
                                               @Param("courseId")Long courseId);

    /**
     * 用户每个任务的得分情况比例
     *
     * @param courseId
     * @param userId
     * @return
     */
    List<Map<String, Object>> findUserGrade(@Param("courseId") Long courseId, @Param("userId") Long userId);

    /**
     * 根据用户id查询班级排名
     *
     * @param sectionId
     * @return
     */
    List<UserByIdVo> userBySection(@Param("sectionId") Long sectionId,@Param("courseId") Long courseId);

    /**
     * 判断是否是小组作业
     *
     * @param assignmentIdList
     * @return
     */
    List<GradeTestListQueryVo> isgGroupAssignmentBatch(List<Long> assignmentIdList);

    /**
     * 判断是否是小组讨论
     *
     * @param discusstionIdList
     * @return
     */
    List<GradeTestListQueryVo> isgGroupDiscussionBatch(List<Long> discusstionIdList);

    /**
     * 批量查询任务的平均分
     * @param originIdList
     * @param originType
     * @return
     */
    List<TaskAvgVO> taskAvgBatch(@Param("originIdList")List<Long> originIdList,@Param("originType") Integer originType);

    /**
     * 查询已评分的人数
     * @param originId 任务ID
     * @param originType 任务类型
     * @param courseId 课程ID
     * @return Integer
     */
    Integer getGradeList(@Param("originId") Long originId,
                         @Param("originType")Integer originType,
                         @Param("courseId")Long courseId);

    /**
     * 批量获取评论数
     * @param originIdList
     * @return
     */
    List<CommentTotalVO> getCommentTotalBatch(@Param("originIdList")List<Long> originIdList);


    int batchInsert(List<GradeComment> gradeComments);
}

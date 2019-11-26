package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.lms.core.base.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserSubmitRecordExtMapper {

    /**
     * 查询课程待评分任务
     *
     * @param courseIds
     * @return
     */
    List<GradeTodoVo> queryGradeTodo(List<Long> courseIds);

    /**
     * 获取已提交的所有学生总数
     */
    Integer getUserSubmitRecordNumber(@Param("originType") Integer originType,
                                      @Param("originId") Long originId,
                                      @Param("studyGroupId") Long studyGroupId);

    /**
     * 获取未评分、已评分的人数比例
     */
    GradeUserSubmitRecordVo getGradedUserSubmitRecordInfo(@Param("originType") Integer originType,
                                                          @Param("originId") Long originId,
                                                          @Param("studyGroupId") Long studyGroupId);

    /**
     * 查询提交状态、时间
     * @param userId 用户ID
     * @param originId 任务ID
     * @param studyGroupId  小组ID
     * @param originType 任务类型
     * @return CosUserSubmitTimeVO
     **/
    CosUserSubmitTimeVO getCosUserSubmitTime(@Param("originType") Integer originType,
                                             @Param("originId") Long originId,
                                             @Param("studyGroupId") Long studyGroupId,
                                             @Param("userId") Long userId);


    /**
     * 查询时间-测验
     **/
    List<Map<String, Object>> getQuizUserSubmitRecordTime(@Param("originType") Integer originType,
                                                          @Param("originId") Long originId,
                                                          @Param("studyGroupId") Long studyGroupId,
                                                          @Param("userId") Long userId);

    /**
     * 查询时间-作业
     **/
    List<Map<String, Object>> getUserSubmitRecordTime(@Param("originType") Integer originType,
                                                      @Param("originId") Long originId,
                                                      @Param("studyGroupId") Long studyGroupId,
                                                      @Param("userId") Long userId);

    /**
     * 查询任务学生-提交
     * @param originType 任务类型
     * @param originId 任务ID
     * @param isGraded 是否评分
     * @param sequence 排序方式
     * @return List<StudentDataListVo>
     */
    List<StudentDataListVo> getGradeStudentList(@Param("originType") Integer originType,
                                                  @Param("originId") Long originId,
                                                  @Param("isGraded") Integer isGraded,
                                                  @Param("sequence") Integer sequence);

    /**
     * 根据作业任务获取学生
     * @param originType 任务类型
     * @param originId 任务ID
     * @param isGraded 是否评分
     * @param studyGroupSetId 小组集ID
     * @return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getGroupStudentList(@Param("originId") Long originId,
                                                  @Param("studyGroupSetId") Long studyGroupSetId,
                                                  @Param("originType") Integer originType,
                                                  @Param("isGraded") Integer isGraded);

    /**
     * 根据提交学生获取组名称-
     **/
    List<Map<String, Object>> getGroupList(@Param("userId") Long userId,
                                           @Param("sequence") Integer sequence,
                                           @Param("courseId") Long courseId);

    /**
     * 查询课程下的所有提交作业的学生List-非小组但是在小组集下面-提交的
     * @param originType 任务类型
     * @param originId 任务ID
     * @param courseId 课程ID
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getNotStudyGroupUserSubmit(@Param("originType") Integer originType,
                                                         @Param("originId") Long originId,
                                                         @Param("courseId") Long courseId);

    /**
     * 批量评分获取提交用户ID-List-包含组
     * @param isGraded 是否评分
     * @param originType 任务类型
     * @param originId 任务ID
     * @param studyGroupId 小组ID
     * @return List<Map<String, Object>> sql中都有固定的值
     */
    List<Map<String, Object>> getUserGradeList(@Param("isGraded") Integer isGraded,
                                               @Param("originType") Integer originType,
                                               @Param("originId") Long originId,
                                               @Param("studyGroupId") Long studyGroupId);

    /**
     * 评分评论小组-已提交、未评分的小组ID
     * @param originType 任务类型
     * @param originId 任务ID
     * @return List<Map<String, Object>> sql中都有固定的值
     */
     List<Map<String, Object>> getNotGradeStudyGroupUserSubmit(@Param("originType") Integer originType,
                                                               @Param("originId") Long originId);


    /**
     * 批量评分获取提交用户ID-List-不包含组
     *
     * @param isGraded 是否评分
     * @param originType 任务类型
     * @param originId 任务ID
     * @return List<Map<String, Object>> sql中都有固定的值
     */
     List<Map<String, Object>> getUserNotGroupGradeList(@Param("isGraded") Integer isGraded,
                                                              @Param("originType") Integer originType,
                                                              @Param("originId") Long originId);

    /**
     * 查询组集下面所有用户
     */
    List<Map<String, Object>> getStudyGroupSetUserList(@Param("studyGroupSetId") Long studyGroupSetId,
                                                       @Param("isGraded") Integer isGraded,
                                                       @Param("originType") Integer originType,
                                                       @Param("originId") Long originId);


    /**
     * 功能：依据originId AND Origintype删除对应记录
     *
     * @param originId   来源ID
     * @param originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @return
     */
    UserSubmitRecord getByOriginIdAndOriginType(@Param("originId") Long originId, @Param("originType") Integer originType);

    /**
     * 统计每个任务已评分和未评分的个数
     **/
    UserSubmitRecordVo countIsGrade(@Param("originId") Long originId, @Param("originIdType") Integer originIdType, @Param("status") Integer status);

    /**
     * 获取课程的评分任务提交数量
     *
     * @param courseIds 查询课程ID
     * @return 课程ID与评分任务提交数量键值对集合
     */
    List<CourseSubmitCountVo> getCourseGradeTaskSubmitCount(@Param("courseIds") Collection<Long> courseIds);



    /**
     * 获取小组提交人员数量-组内1人提交就代表组内全部人员提交 说明：取消重复的组
     * @param originType
     * @param originId
     * @param courseId
     * @return
     */
    List<Map<String,Object>> getSubmitStudyGroupDistinct(@Param("originType") Integer originType,
                                                         @Param("originId") Long originId,
                                                         @Param("courseId") Long courseId);
    /*
     * 查询每个人作业提交的情况
     * 如果是小组任务，study_group_id有值，只要一个人提交就算提交
     * */
    UserSubmitRecordVo findUserSubmitRecord(@Param("courseId") Long courseId, @Param("originId") Long originId,
                                            @Param("originIdType") Integer originIdType, @Param("userId") Long userId);

    /**
     * 查询学生提交作业的情况统计（几次迟交，按时交）
     **/
    List<UserSubmitRecordVo> submitRecordQuery(@Param("courseId") Long courseId, @Param("userId") Long userId);

    /**
     * 根据交集查询这个任务的提交情况
     * @param courseId
     * @param originId
     * @param originIdType
     * @param stuIds
     * @return
     */
    List<UserSubmitRecordVo> findUserSubmitRecordByStuIds(@Param("courseId") Long courseId, @Param("originId") Long originId,
    @Param("originIdType") Integer originIdType, @Param("stuIds")  Set<Long>  stuIds);

    /**
     * 批量统计每个任务已评分和未评分的个数
     * @param originIds
     * @return
     */
    List<UserSubmitRecordVo> countIsGradeBatch(@Param("originIds")List<Long> originIds);

    Integer isSubmited(@Param("originType") Long originType, @Param("originId") Long originId);
}

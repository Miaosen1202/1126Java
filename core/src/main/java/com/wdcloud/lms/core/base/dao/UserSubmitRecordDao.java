package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserSubmitRecordExtMapper;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.lms.core.base.vo.*;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserSubmitRecordDao extends CommonDao<UserSubmitRecord, Long> {
    @Autowired
    private UserSubmitRecordExtMapper userSubmitRecordExtMapper;

    public List<GradeTodoVo> queryGradeTodo(List<Long> courseIds) {
        if (ListUtils.isEmpty(courseIds)) {
            return new ArrayList<>();
        }
        List<GradeTodoVo> result = userSubmitRecordExtMapper.queryGradeTodo(courseIds);
        result = result.stream().filter(x -> x.getCourseId() != null).collect(Collectors.toList());
        return result;
    }

    public UserSubmitRecord deleteByOriginIdAndOriginType(Long originId, Integer originType) {
        return userSubmitRecordExtMapper.getByOriginIdAndOriginType(originId, originType);
    }


    /**
     * 获取已提交的所有学生总数
     */
    public Integer getUserSubmitRecordCount(Integer originType, Long originId, Long studyGroupId) {
        return userSubmitRecordExtMapper.getUserSubmitRecordNumber(originType, originId, studyGroupId);
    }


    /**
     * 获取未评分、已评分的人数比例
     */
    public GradeUserSubmitRecordVo getGradedUserSubmitRecord(Integer originType, Long originId, Long studyGroupId) {
        return userSubmitRecordExtMapper.getGradedUserSubmitRecordInfo(originType, originId, studyGroupId);
    }

    /**
     * 查询提交状态、时间
     *
     * @param userId       用户ID
     * @param originId     任务ID
     * @param studyGroupId 小组ID
     * @param originType   任务类型
     * @return CosUserSubmitTimeVO
     */
    public CosUserSubmitTimeVO getCosUserSubmitTime(Integer originType, Long originId, Long studyGroupId, Long userId) {
        return userSubmitRecordExtMapper.getCosUserSubmitTime(originType, originId, studyGroupId, userId);

    }

    /**
     * 查询任务学生-提交
     *
     * @param originType 任务类型
     * @param originId   任务ID
     * @param isGraded   是否评分
     * @param sequence   排序方式
     * @return List<StudentDataListVo>
     */
    public List<StudentDataListVo> getGradeStudentList(Integer originType, Long originId,
                                                       Integer isGraded, Integer sequence) {
        return userSubmitRecordExtMapper.getGradeStudentList(originType, originId, isGraded, sequence);
    }


    /**
     * 查询时间-测验
     **/
    public List<Map<String, Object>> getQuizUserSubmitRecordTime(Integer originType, Long originId, Long studyGroupId, Long userId) {
        return userSubmitRecordExtMapper.getQuizUserSubmitRecordTime(originType, originId, studyGroupId, userId);

    }

    /**
     * 查询时间-作业
     **/
    public List<Map<String, Object>> getUserSubmitRecordTime(Integer originType, Long originId, Long studyGroupId, Long userId) {
        return userSubmitRecordExtMapper.getUserSubmitRecordTime(originType, originId, studyGroupId, userId);

    }

    /**
     * 根据作业任务获取学生
     *
     * @param originType      任务类型
     * @param originId        任务ID
     * @param isGraded        是否评分
     * @param studyGroupSetId 小组集ID
     * @return List<Map       <       String       ,               Object>>
     **/
    public List<Map<String, Object>> getGroupStudentList(Long originId, Long studyGroupSetId,
                                                         Integer originType, Integer isGraded) {
        return userSubmitRecordExtMapper.getGroupStudentList(originId, studyGroupSetId, originType, isGraded);
    }

    /**
     * 查询组集下面所有用户
     */
    public List<Map<String, Object>> getStudyGroupSetUserList(Long studyGroupSetId,
                                                              Integer isGraded,
                                                              Integer originType,
                                                              Long originId) {
        return userSubmitRecordExtMapper.getStudyGroupSetUserList(studyGroupSetId, isGraded, originType, originId);
    }

    /**
     * 批量评分获取提交用户ID-List-包含组
     *
     * @param isGraded     是否评分
     * @param originType   任务类型
     * @param originId     任务ID
     * @param studyGroupId 小组ID
     * @return List<Map   <   String   ,       Object>> sql中都有固定的值
     */
    public List<Map<String, Object>> getUserGradeList(Integer isGraded,
                                                      Integer originType,
                                                      Long originId,
                                                      Long studyGroupId) {
        return userSubmitRecordExtMapper.getUserGradeList(isGraded, originType, originId, studyGroupId);
    }


    /**
     * 评分评论小组-已提交、未评分的小组ID
     *
     * @param originType 任务类型
     * @param originId   任务ID
     * @return List<Map   <   String   ,       Object>> sql中都有固定的值
     */
    public List<Map<String, Object>> getNotGradeStudyGroupUserSubmit(Integer originType, Long originId) {
        return userSubmitRecordExtMapper.getNotGradeStudyGroupUserSubmit(originType, originId);
    }

    /**
     * 批量评分获取提交用户ID-List-不包含组
     *
     * @param isGraded   是否评分
     * @param originType 任务类型
     * @param originId   任务ID
     * @return List<Map   <   String   ,       Object>> sql中都有固定的值
     */
    public List<Map<String, Object>> getUserNotGroupGradeList(Integer isGraded, Integer originType, Long originId) {
        return userSubmitRecordExtMapper.getUserNotGroupGradeList(isGraded, originType, originId);
    }


    /**
     * 根据提交学生获取组名称-
     *
     * @param userId   用户ID
     * @param courseId 课程ID
     * @param sequence 排序方式
     * @return List sql中都有固定的值
     **/
    public List<Map<String, Object>> getGroupList(Long userId, Integer sequence, Long courseId) {
        return userSubmitRecordExtMapper.getGroupList(userId, sequence, courseId);
    }

    /**
     * 查询课程下的所有提交作业的学生List-非小组但是在小组集下面-提交的
     *
     * @param originType 任务类型
     * @param originId   任务ID
     * @param courseId   课程ID
     * @return List sql中都有固定的值
     */
    public List<Map<String, Object>> getNotStudyGroupUserSubmit(Integer originType,
                                                                Long originId,
                                                                Long courseId) {
        return userSubmitRecordExtMapper.getNotStudyGroupUserSubmit(originType, originId, courseId);
    }

    /**
     * 统计每个任务已评分和未评分的个数
     **/
    public UserSubmitRecordVo countIsGrade(Long originId, Integer originIdType, Integer status) {
        return userSubmitRecordExtMapper.countIsGrade(originId, originIdType, status);
    }

    public List<CourseSubmitCountVo> getCourseGradeTaskSubmitCount(Collection<Long> courseIds) {
        return userSubmitRecordExtMapper.getCourseGradeTaskSubmitCount(courseIds);
    }

    public List<UserSubmitRecord> findByOriginIdAndOriginType(Long originId, Integer originType, Long userId) {
        return find(UserSubmitRecord.builder().userId(userId).originType(originType).originId(originId).build());
    }

    /**
     * 获取小组提交人员-组内1人提交就代表组内全部人员提交
     * @param originType
     * @param originId
     * @param courseId
     * @return
     */
    public List<Map<String, Object>> getSubmitStudyGroupDistinct(Integer originType, Long originId, Long courseId) {
        return userSubmitRecordExtMapper.getSubmitStudyGroupDistinct(originType, originId, courseId);

    }

    /*
     * 查询每个人作业提交的情况
     * 如果是小组任务，study_group_id有值，只要一个人提交就算提交
     * */
    public UserSubmitRecordVo findUserSubmitRecord(Long courseId, Long originId, Integer originType, Long userId) {
        return userSubmitRecordExtMapper.findUserSubmitRecord(courseId, originId, originType, userId);
    }

    /**
     * 查询学生提交作业的情况统计（几次迟交，按时交）
     **/
    public List<UserSubmitRecordVo> submitRecordQuery(Long courseId, Long userId) {
        return userSubmitRecordExtMapper.submitRecordQuery(courseId, userId);
    }

    @Override
    public Class<UserSubmitRecord> getBeanClass() {
        return UserSubmitRecord.class;
    }

    /**
     * 根据交集查询这个任务的提交情况
     * @param courseId
     * @param originId
     * @param originType
     * @param stuIds
     * @return
     */
    public List<UserSubmitRecordVo> findUserSubmitRecordByStuIds(Long courseId, Long originId, Integer originType, Set<Long> stuIds) {
        return userSubmitRecordExtMapper.findUserSubmitRecordByStuIds(courseId, originId, originType, stuIds);
    }

    /**
     * 批量统计每个任务已评分和未评分的个数
     * @param originIds
     * @return
     */
    public List<UserSubmitRecordVo> countIsGradeBatch(List<Long> originIds) {
        return userSubmitRecordExtMapper.countIsGradeBatch(originIds);
    }

    public Integer isSubmited(Long originType, Long originId) {
        return userSubmitRecordExtMapper.isSubmited(originType,originId);
    }
}
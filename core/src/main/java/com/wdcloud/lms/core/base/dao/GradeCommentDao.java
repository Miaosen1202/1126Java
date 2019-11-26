package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.GradeCommentExtMapper;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.vo.*;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class GradeCommentDao extends CommonDao<GradeComment, Long> {
    @Autowired
    private GradeCommentExtMapper gradeCommentExtMapper;

    public List<GradeCommentVO> findAssignmentComment(Map<String, Object> map) {
        return gradeCommentExtMapper.findAssignmentComment(map);
    }

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
    public GradedSummeryDataVo getGradedSummeryData(Integer originType, Long originId, Long studyGroupId,Integer isGraded,Long courseId,Long userId) {
        return gradeCommentExtMapper.getGradedSummeryData(originType, originId, studyGroupId,isGraded,courseId,userId);
    }


    /**
     * 小组内评分人员总数
     * @param courseId 课程ID
     * @param originId 任务ID
     * @param originType 任务类型
     * @param studyGroupId 小组ID
     * @return List<Map<String,Object>>
     */
    public List<Map<String,Object>> getGradeGroupCount(Long courseId,Long originId,Integer originType,Long studyGroupId){
        return gradeCommentExtMapper.getGradeGroupCount(courseId, originId, originType,studyGroupId);
    }

    /**
     * 获取评论信息
     * @param userId 用户id
     * @param originType 任务类型
     * @param originId 任务ID
     * @return List<GradeCommentListVo>
     * */
    public List<GradeCommentListVo> getGradeCommentList(Long userId, Long originType, Long originId) {
        return gradeCommentExtMapper.getGradeCommentList(userId, originType, originId);
    }

    /**
     * 获取已评分的用户信息——List
     */
    public List<Map<String, Object>> getUserSubmitGradeList(Integer originType,
                                                     Long originId,
                                                     Long assignmentGroupItemId) {
        return gradeCommentExtMapper.getUserSubmitGradeList(originType, originId, assignmentGroupItemId);
    }

    /**
     *  获取我的评分信息
     * @param userId 用户ID
     * @param courseId 课程ID
     * @return List<MyGradeBookListVo>
     */
    public List<MyGradeBookListVo> getMyGradeBookList(Long userId, Long courseId) {
        return gradeCommentExtMapper.getMyGradeBookList(userId, courseId);
    }

    @Override
    public Class<GradeComment> getBeanClass() {
        return GradeComment.class;
    }

    /**
     * 批量获取评论数
     * @param originIdList
     * @return
     */
    public List<CommentTotalVO> getCommentTotalBatch(List<Long> originIdList) {
        return gradeCommentExtMapper.getCommentTotalBatch(originIdList);
    }

    public void batchInsert(List<GradeComment> gradeComments) {
        if (ListUtils.isNotEmpty(gradeComments)) {
            gradeCommentExtMapper.batchInsert(gradeComments);
        }
    }
}
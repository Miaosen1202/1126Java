package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.vo.AssignmentGroupItemVo;
import com.wdcloud.lms.core.base.vo.UserSubmissionVo;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface AssignmentGroupItemExtMapper {

    List<AssignmentGroupItem> getByCourseIdAndOriginType(@Param("courseId") long courseId, @Param("originType") String originType);

    /**
     * 查询课程作业项数量
     * @param courseIds
     * @retur   n
     */
    List<Map<String, Long>> findCourseAssignmentGroupItemNumber(List<Long> courseIds);

    Integer updateBatchSeqAndGroupId(List<AssignmentGroupItem> items);

    Integer getMaxSeq(Long assignmentGroupId);
    Integer getMinSeq(Long assignmentGroupId);

    List<AssignmentGroupItem> findUserUnsubmitItems(@Param("courseId") Long courseId, @Param("userId") Long userId);

//    List<AssignmentGroupItemVo> findItemVoByCourseId(@Param("courseId") Long courseId, @Param("status") @Nullable Integer status);

//    List<UserSubmissionVo> findUserSubmissionSummary(@Param("courseId") Long courseId,
//                                                                   @Param("userId") Long userId,
//                                                                   @Param("status") @Nullable Integer status);
//    List<UserSubmissionVo> findUserSubmissionSummaryList(@Param("courseId") Long courseId,
//                                                     @Param("userIds") String userIds,
//                                                     @Param("status") @Nullable Integer status);



    List<Map<String, Object>> findUserIdTask(@Param("courseId") Long courseId,
                                          @Param("userId") Long userId,
                                          @Param("status") @Nullable Integer status);

    public List<AssignmentGroupItem> findAssignmentGroupItemList(Map<String, Object> map);

    public List<AssignmentGroupItem> findAssignmentGroupItemListByCourse(Map<String, Object> map);
}
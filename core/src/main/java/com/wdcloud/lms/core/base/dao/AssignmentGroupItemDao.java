package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentGroupItemExtMapper;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.vo.UserSubmissionVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AssignmentGroupItemDao extends CommonDao<AssignmentGroupItem, Long> {

    @Autowired
    private AssignmentGroupItemExtMapper assignmentGroupItemExtMapper;

    public AssignmentGroupItemExtMapper ext() {
        return assignmentGroupItemExtMapper;
    }

    public Map<Long, Integer> findCourseAssignmentGroupItemNumber(List<Long> courseIds) {
        if (ListUtils.isEmpty(courseIds)) {
            return new HashMap<>();
        }
        List<Map<String, Long>> groupItemNumbers = assignmentGroupItemExtMapper.findCourseAssignmentGroupItemNumber(courseIds);
        Map<Long, Integer> result = new HashMap<>(groupItemNumbers.size());
        for (Map<String, Long> groupItemNumber : groupItemNumbers) {
            result.put(groupItemNumber.get("courseId"), groupItemNumber.get("assignmentGroupItemNumber").intValue());
        }
        return result;
    }

    public int updateBatchSeqAndGroupId(List<AssignmentGroupItem> items) {
        return assignmentGroupItemExtMapper.updateBatchSeqAndGroupId(items);
    }
    public int updateSeq(long id, long groupId, int seq) {
        AssignmentGroupItem moduleItem = new AssignmentGroupItem();
        moduleItem.setId(id);
        moduleItem.setSeq(seq);
        moduleItem.setAssignmentGroupId(groupId);
        return update(moduleItem);
    }

    public List<AssignmentGroupItem> getAssignmentGroupItemOrderBySeq(long assignmentGroupId) {
        final Example example = getExample();
        example.orderBy("seq");
        example.createCriteria().andEqualTo("assignmentGroupId", assignmentGroupId);
        return find(example);
    }

    public List<AssignmentGroupItem> findUserUnsubmitItems(Long courseId, Long userId) {
        return assignmentGroupItemExtMapper.findUserUnsubmitItems(courseId, userId);
    }

    public AssignmentGroupItem findByOriginIdAndType(Long originId, OriginTypeEnum originType) {
        return findOne(AssignmentGroupItem.builder().originType(originType.getType()).originId(originId).build());
    }
    public AssignmentGroupItem findByOriginIdAndTypeId(Long originId, Integer originType) {
        return findOne(AssignmentGroupItem.builder().originType(originType).originId(originId).build());
    }

//    public List<UserSubmissionVo> findUserSubmissionSummary(Long courseId, Long userId, Status status) {
//        return assignmentGroupItemExtMapper.findUserSubmissionSummary(courseId, userId, status == null ? null : status.getStatus());
//    }

//    public List<UserSubmissionVo> findUserSubmissionSummaryList(Long courseId, String userIds, Status status) {
//        return assignmentGroupItemExtMapper.findUserSubmissionSummaryList(courseId, userIds, status == null ? null : status.getStatus());
//    }

//    public List<AssignmentGroupItem> findByCourseId(Long courseId, Status status) {
//        return assignmentGroupItemExtMapper.findByCourseId(courseId, status == null ? null : status.getStatus());
//    }

    public List<Map<String, Object>> findUserIdTask(Long courseId, Long userId, Status status) {
        return assignmentGroupItemExtMapper.findUserIdTask(courseId, userId, status == null ? null : status.getStatus());
    }

    @Override
    protected Class<AssignmentGroupItem> getBeanClass() {
        return AssignmentGroupItem.class;
    }

    public List<AssignmentGroupItem> findAssignmentGroupItemList(Map<String, Object> map) {
        return assignmentGroupItemExtMapper.findAssignmentGroupItemList(map);
    }

    public List<AssignmentGroupItem> findAssignmentGroupItemListByCourse(Map<String, Object> map) {
        return assignmentGroupItemExtMapper.findAssignmentGroupItemListByCourse(map);
    }
}
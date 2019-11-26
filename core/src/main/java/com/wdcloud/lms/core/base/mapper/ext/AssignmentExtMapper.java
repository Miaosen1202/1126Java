package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.vo.AssignmentVO;

import java.util.List;
import java.util.Map;

public interface AssignmentExtMapper {

    List<AssignmentVO> findCalendarAssignmentList(Map<String, Object> map);

    List<AssignmentVO> findCalendarAssignmentListStudent(Map<String, Object> map);

    List<Assignment> finAssignmentSelectByGroupId(Map<String, Object> map);
}

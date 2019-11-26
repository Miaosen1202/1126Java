package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentExtMapper;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.vo.AssignmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AssignmentDao extends CommonDao<Assignment, Long> {
    @Autowired
    private AssignmentExtMapper assignmentExtMapper;


    public List<AssignmentVO> findCalendarAssignmentList(Map<String, Object> map) {
        return assignmentExtMapper.findCalendarAssignmentList(map);
    }

    public List<AssignmentVO> findCalendarAssignmentListStudent(Map<String, Object> map) {
        return assignmentExtMapper.findCalendarAssignmentListStudent(map);
    }

    public List<Assignment> finAssignmentSelectByGroupId(Map<String, Object> map) {
        return assignmentExtMapper.finAssignmentSelectByGroupId(map);
    }

    @Override
    protected Class<Assignment> getBeanClass() {
        return Assignment.class;
    }
}
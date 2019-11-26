package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentExtMapper;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Syllabus;
import com.wdcloud.lms.core.base.vo.AssignmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SyllabusDao extends CommonDao<Syllabus, Long> {

    @Override
    protected Class<Syllabus> getBeanClass() {
        return Syllabus.class;
    }
}
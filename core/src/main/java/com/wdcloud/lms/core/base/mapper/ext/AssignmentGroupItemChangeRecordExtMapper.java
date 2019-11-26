package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.AssignmentGroupItemChangeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssignmentGroupItemChangeRecordExtMapper {

    List<AssignmentGroupItemChangeRecord> findNotifies(@Param("courseId") Long courseId);
}

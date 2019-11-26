package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.vo.Tuple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssignExtMapper {

    int batchInsert(List<Assign> assignList);

    List<Assign> findCourseAssign(@Param("courseId") Long courseId, @Param("originTypeAndIds") List<Tuple<OriginTypeEnum, Long>> originTypeAndIds);
    /**
     * 查询这个任务分配的学生
     *
     * @param originId,originType,courseId
     * @return
     */
    List<AssignUser> findTask(@Param("originId") Long originId, @Param("originType") int originType,@Param("courseId") Long courseId);

}

package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.StudyGroupSet;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface StudyGroupSetExtMapper {

    /**
     * 根据查询条件（导入的人员id），查询机构及子机构下的小组集
     * @param rootOrgTreeId 机构id
     * @param sisIds 导入的人员id
     * @return List<StudyGroupSet>
     */
    List<StudyGroupSet> findBySisIds(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);
}

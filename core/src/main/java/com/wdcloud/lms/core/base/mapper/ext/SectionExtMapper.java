package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.vo.SectionVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SectionExtMapper {

    /**
     * 通过课程id查询班级
     * @param param
     * @return List<SectionVo>
     */
    List<SectionVo> findSectionListByCourseId(Map<String, String> param);

    /**
     * 根据查询条件（人员导入id）,查询该机构及子机构下的班级
     * @param rootOrgTreeId 机构id
     * @param sisIds 人员导入id
     * @return List<Section>
     */
    List<Section> findBySisIds(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);
}

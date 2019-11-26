package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SectionExtMapper;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.vo.SectionVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Repository
public class SectionDao extends CommonDao<Section, Long> {
    @Autowired
    private SectionExtMapper sectionExtMapper;

    public List<Section> findCourseSections(Collection<Long> ids, Long courseId) {
        if (ids == null || ids.isEmpty() || courseId == null) {
            return Collections.emptyList();
        }

        Example example = getExample();
        example.createCriteria()
                .andIn(Section.ID, ids)
                .andEqualTo(Section.COURSE_ID, courseId);
        return find(example);
    }

    @Override
    protected Class<Section> getBeanClass() {
        return Section.class;
    }

    public List<SectionVo> findSectionListByCourseId(Map<String, String> param){
        return sectionExtMapper.findSectionListByCourseId(param);
    }
    public SectionExtMapper ext() {
        return this.sectionExtMapper;
    }

    public List<Section> findBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        return sectionExtMapper.findBySisIds(rootOrgTreeId, sisIds);
    }

    public int deleteByCourseIds(Collection<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return 0;
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(Section.COURSE_ID, courseIds);
        return delete(example);
    }
}
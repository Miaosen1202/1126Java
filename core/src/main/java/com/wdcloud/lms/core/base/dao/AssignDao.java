package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.mapper.ext.AssignExtMapper;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.vo.Tuple;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class AssignDao extends CommonDao<Assign, Long> {
    @Autowired
    private AssignExtMapper assignExtMapper;

    public void batchInsert(List<Assign> assignList) {
        if (ListUtils.isNotEmpty(assignList)) {
            assignExtMapper.batchInsert(assignList);
        }
    }

    public List<Assign> find(long courseId, int assignType, long assignId) {
        return find(Assign.builder().courseId(courseId).assignType(assignType).assignId(assignId).build());
    }
    public List<Assign> findByCourseId(long courseId) {
        return find(Assign.builder().courseId(courseId).build());
    }

    public List<Assign> findAssigns(Long courseId, List<Tuple<OriginTypeEnum, Long>> originTypeAndIds) {
        if (courseId == null || ListUtils.isEmpty(originTypeAndIds)) {
            return new ArrayList<>();
        }
        return assignExtMapper.findCourseAssign(courseId, originTypeAndIds);
    }
    public List<Assign> getAssigns(Collection<String> courseIds, OriginTypeEnum originTypeEnum) {
        Example example = getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(Assign.COURSE_ID, courseIds);
        criteria.andEqualTo(Assign.ORIGIN_TYPE, originTypeEnum.getType());
        return find(example);
    }

    /**
     * 查询这个任务分配的学生
     * @param originId,originType,courseId
     * @return
     */
    public List<AssignUser> findTask(long originId, int originType,Long courseId){
        return assignExtMapper.findTask(originId, originType,courseId);
    }

    @Override
    protected Class<Assign> getBeanClass() {
        return Assign.class;
    }
}
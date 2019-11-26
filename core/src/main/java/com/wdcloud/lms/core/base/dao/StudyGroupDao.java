package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.StudyGroupExtMapper;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.vo.StudyGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class StudyGroupDao extends CommonDao<StudyGroup, Long> {
    @Autowired
    private StudyGroupExtMapper studyGroupExtMapper;

    /**
     * 查询用户加入所有学习小组
     * @param userId
     * @return
     */
    public List<StudyGroup> findJoined(Long userId, Long courseId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        return studyGroupExtMapper.findJoined(userId, courseId);
    }

    public List<StudyGroup> findBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        return studyGroupExtMapper.findBySisIds(rootOrgTreeId, sisIds);
    }

    public int deleteByCourseIds(Collection<Long> courseIds) {
        Example example = getExample();
        example.createCriteria()
                .andIn(StudyGroup.COURSE_ID, courseIds);
        return delete(example);
    }

    public void updateMaxMemberNumber(Long studyGroupSetId, Integer maxMemberNumber) {
        if (maxMemberNumber != null && maxMemberNumber <= 0) {
            return;
        }

        studyGroupExtMapper.updateMaxMemberNumber(studyGroupSetId, maxMemberNumber);
    }

    /**
     * 根据study_group_set_id 查询发布的小组列表
     * @param studyGroupSetId 小组集ID
     * @return
     */
    public List<StudyGroup> findStudyGroupList(Long studyGroupSetId){
        if (studyGroupSetId == null){
            return new ArrayList<>();
        }
        return studyGroupExtMapper.findStudyGroupList(studyGroupSetId);
    }

    @Override
    protected Class<StudyGroup> getBeanClass() {
        return StudyGroup.class;
    }

    public List<StudyGroupVO> findStudyGroupListBySetId(Long studyGroupSetId) {
        return studyGroupExtMapper.findStudyGroupListBySetId(studyGroupSetId);
    }

    public List<String> findStudyGroupByGroupSetAndUser(Long userId, Long studyGroupSetId) {
        return studyGroupExtMapper.findStudyGroupByGroupSetAndUser(userId, studyGroupSetId);
    }
}
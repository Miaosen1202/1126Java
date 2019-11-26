package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.vo.StudyGroupVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface StudyGroupExtMapper {
    /**
     *  根据查询条件（课程id），查询某个人员已加入的学习小组
     * @param userId 人员id
     * @param courseId 课程id
     * @return List<StudyGroup>
     */
    List<StudyGroup> findJoined(@Param("userId") Long userId, @Param("courseId") Long courseId);

    /**
     * 更新某个小组集的最大人数
     * @param studyGroupSetId 小组集id
     * @param maxMemberNumber 最大人数
     */
    void updateMaxMemberNumber(@Param("studyGroupSetId") Long studyGroupSetId, @Param("maxMemberNumber") Integer maxMemberNumber);

    /**
     * 根据study_group_set_id 查询发布的小组列表
     * @param studyGroupSetId 小组集ID
     * @return List<StudyGroup>
     */
    List<StudyGroup> findStudyGroupList(@Param("studyGroupSetId") Long studyGroupSetId);

    /**
     * 根据查询条件（人员导入id），查询机构及子机构下的学习小组
     * @param rootOrgTreeId 机构id
     * @param sisIds 人员导入id
     * @return List<StudyGroup>
     */
    List<StudyGroup> findBySisIds(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);

    /**
     * 根据小组集id查询小组
     * @param studyGroupSetId 小组集id
     * @return List<StudyGroupVO>
     */
    List<StudyGroupVO> findStudyGroupListBySetId(@Param("studyGroupSetId")Long studyGroupSetId);

    List<String> findStudyGroupByGroupSetAndUser(@Param("userId") Long userId, @Param("studyGroupSetId") Long studyGroupSetId);
}

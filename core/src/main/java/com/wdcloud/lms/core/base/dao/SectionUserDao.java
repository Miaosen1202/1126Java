package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.mapper.ext.SectionUserExtMapper;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SectionUserDao extends CommonDao<SectionUser, Long> {
    @Autowired
    private SectionUserExtMapper sectionUserExtMapper;

    public int updateSectionUserUserId(Long oldUser, Long newUser, Long courseId, Long sectionId, Long opUserId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(SectionUser.COURSE_ID, courseId)
                .andEqualTo(SectionUser.SECTION_ID, sectionId)
                .andEqualTo(SectionUser.USER_ID, oldUser);
        return mapper.updateByExampleSelective(SectionUser.builder().userId(newUser).updateUserId(opUserId).build(), example);
    }

    public int updateSectionUserJoinStatus(Long courseId, Long sectionId, Long userId, Long roleId,
                                           UserRegistryStatusEnum status, Long opUserId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(SectionUser.COURSE_ID, courseId)
                .andEqualTo(SectionUser.SECTION_ID, sectionId)
                .andEqualTo(SectionUser.USER_ID, userId)
                .andEqualTo(SectionUser.ROLE_ID, roleId);
        return mapper.updateByExampleSelective(
                SectionUser.builder()
                        .registryStatus(status.getStatus())
                        .updateUserId(opUserId)
                        .build(),
                example);
    }

    /**
     * 根据课程查询班级用户 并且是注册状态的
     *
     * @param courseId
     * @return
     */
    public List<User> getUserByCourseId(Long courseId) {
        return sectionUserExtMapper.getUserByCourseId(courseId,null);
    }

    /**
     * 查询课程下的用户集合 roleId==null查所有用户，roleId=老师|学生，则查询对应的用户集合
     * @param courseId
     * @param roleId
     * @return
     */
    public List<User> getUsersByCourseIdAndRoleId(Long courseId,Long roleId) {
        return sectionUserExtMapper.getUserByCourseId(courseId, roleId);
    }
    public List<SectionUser> findUserByCourseId(Long courseId) {
        return sectionUserExtMapper.findUserByCourseId(courseId);
    }

    /**
     * 功能：目前主要给用户分配用
     * @param courseId
     * @param sectionId
     * @return
     */
    public List<SectionUser> findUserByCourseIdAndSectionId(Long courseId, Long sectionId) {
        return find(SectionUser.builder().courseId(courseId).sectionId(sectionId).build());
    }

    /**
     * 功能：目前用于用户分配查找
     * @param courseId
     * @param userId
     * @return
     */
    public List<SectionUser> findUserByCourseIdAndUserId(Long courseId,  Long userId) {
        return find(SectionUser.builder().courseId(courseId).userId(userId).build());
    }
    public int deleteByCourseIds(Collection<Long> courseIds) {
        Example example = getExample();
        example.createCriteria()
                .andIn(SectionUser.COURSE_ID, courseIds);
        return delete(example);
    }


    @Override
    protected Class<SectionUser> getBeanClass() {
        return SectionUser.class;
    }

    public List<Section> findSectionsByThisStudentJoined(String courseId, Long userId, Long roleId) {
        return sectionUserExtMapper.findSectionsByThisStudentJoined(courseId,userId,roleId);
    }

    /**
     * 根据课程查询班级用户 并且是注册状态的
     *
     * @param courseId
     * @return
     */
    public List<User> getUserBySectionId(Long courseId, List<Long> sectionIds) {
        return sectionUserExtMapper.getUserBySectionId(courseId,sectionIds);
    }
}
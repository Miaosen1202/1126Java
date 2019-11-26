package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.CourseUserExtMapper;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.vo.CourseUserDetailVo;
import com.wdcloud.lms.core.base.vo.UserCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class CourseUserDao extends CommonDao<CourseUser, Long> {
    @Autowired
    private CourseUserExtMapper courseUserExtMapper;

    public List<CourseUserDetailVo> findCourseUserDetail(Map<String, Object> condition) {
        return courseUserExtMapper.findCourseUserDetail(condition);
    }

    public List<CourseUser> findNotPendingUsers(Long courseId, Long roleId) {
        return courseUserExtMapper.findNotPendingUsers(courseId, roleId);
    }

    public List<CourseUser> findUserByCourseId(Long courseId) {
        return courseUserExtMapper.findUserByCourseId(courseId);
    }

    public void updateActiveStatus(Long courseId, Long userId, Status activeStatus) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUser.COURSE_ID, courseId)
                .andEqualTo(CourseUser.USER_ID, userId);
        mapper.updateByExampleSelective(CourseUser.builder().isActive(activeStatus.getStatus()).build(), example);
    }

    public CourseUser getByUserId(Long courseId, Long userId) {
        return findOne(CourseUser.builder().courseId(courseId).userId(userId).build());
    }

    /**
     * 获取用户下的课程
     * @param userId 用户ID
     * @return List<UserCourseVo>
     */
    public List<UserCourseVo> getUserCourseList(Long userId){
        return courseUserExtMapper.getUserCourseList(userId);
    }

    public int setCourseFavorite(Long courseId, Long userId, Status favorite) {
        if (courseId == null) {
            return 0;
        }
        List<Long> list = new ArrayList<>();
        list.add(courseId);
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUser.COURSE_ID, courseId)
                .andEqualTo(CourseUser.USER_ID, userId);
        return mapper.updateByExampleSelective(CourseUser.builder().isFavorite(favorite.getStatus()).build(), example);
    }

    /**
     * 按课程ID来更新record
     *
     * @param courseId 课程ID
     * @param record   更新字段（非空进行更新）
     * @return 更新数据量s
     */
    public int update(long courseId, long userId, CourseUser record) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUser.COURSE_ID, courseId)
                .andEqualTo(CourseUser.USER_ID, userId);
        return mapper.updateByExampleSelective(record, example);
    }

    public void updateUserCourseAlias(Long userId, Long courseId, String alias) {
        if (userId == null || courseId == null || alias == null) {
            return;
        }

        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUser.COURSE_ID, courseId)
                .andEqualTo(CourseUser.USER_ID, userId);

        CourseUser record = CourseUser.builder().courseAlias(alias).build();
        mapper.updateByExampleSelective(record, example);
    }

    public boolean hasUser(Long courseId, Long userId) {
        if (courseId == null || userId == null) {
            return false;
        }

        return count(CourseUser.builder().courseId(courseId).userId(userId).build()) > 0;
    }

    public void deleteByCourseIds(Collection<Long> courseIds) {
        Example example = getExample();
        example.createCriteria()
                .andIn(CourseUser.COURSE_ID, courseIds);
        delete(example);
    }

    public void deleteUnjoinSectionUsers(Collection<Long> courseIds) {
        courseUserExtMapper.deleteUnjoinSectionUsers(courseIds);
    }

    @Override
    protected Class<CourseUser> getBeanClass() {
        return CourseUser.class;
    }

    public List<CourseUser> findBySisIds(String rootOrgTreeId, Collection<String> userSisIds) {
        return courseUserExtMapper.findBySisIds(rootOrgTreeId, userSisIds);
    }
}

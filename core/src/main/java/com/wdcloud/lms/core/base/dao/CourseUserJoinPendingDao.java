package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.CourseUserJoinPendingExtMapper;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.vo.CourseUserJoinPendingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class CourseUserJoinPendingDao extends CommonDao<CourseUserJoinPending, Long> {

    @Autowired
    private CourseUserJoinPendingExtMapper courseUserJoinPendingExtMapper;

    public void update(Long courseId, Status publicStatus) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUserJoinPending.COURSE_ID, courseId);
        mapper.updateByExampleSelective(CourseUserJoinPending.builder().publicStatus(publicStatus.getStatus()).build(), example);
    }

    public CourseUserJoinPending getByCode(String code) {
        return findOne(CourseUserJoinPending.builder().code(code).build());
    }

    public void updateUserInviteToNewUser(Long oldUser, Long newUser) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(CourseUserJoinPending.USER_ID, oldUser);
        mapper.updateByExampleSelective(CourseUserJoinPending.builder().userId(newUser).build(), example);
    }

    public List<CourseUserJoinPendingVO> getInvitation(long userId,Long roleId) {
        return courseUserJoinPendingExtMapper.getInvitation(userId,roleId);
    }

    @Override
    protected Class<CourseUserJoinPending> getBeanClass() {
        return CourseUserJoinPending.class;
    }
}
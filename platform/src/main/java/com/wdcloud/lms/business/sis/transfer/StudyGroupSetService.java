package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

@Component
public class StudyGroupSetService {

    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserFileDao userFileDao;

    public int deleteGroupSetByCourseIds(Collection<Long> courseIds) {
        deleteGroupByCourseIds(courseIds);
        return studyGroupSetDao.deleteByCourseIds(courseIds);
    }

    public int deleteGroupSetByGroupSetIds(Collection<Long> groupSetIds) {
        deleteGroupByGroupSetIds(groupSetIds);
        return studyGroupSetDao.deletes(groupSetIds);
    }

    public int deleteGroupByCourseIds(Collection<Long> courseIds) {
        deleteGroupUserByCourseIds(courseIds);
        return studyGroupDao.deleteByCourseIds(courseIds);
    }

    public int deleteGroupByGroupSetIds(Collection<Long> groupSetIds) {
        Example example = studyGroupDao.getExample();
        example.createCriteria()
                .andIn("studyGroupSetId", groupSetIds);
        return studyGroupDao.delete(example);
    }

    public int deleteGroupByGroupIds(Collection<Long> groupIds) {
        deleteGroupUserByGroupIds(groupIds);
        return studyGroupDao.deletes(groupIds);
    }

    public int deleteGroupUserByCourseIds(Collection<Long> courseIds) {
        return studyGroupUserDao.deleteByCourseIds(courseIds);
    }

    public int deleteGroupUserByGroupSetIds(Collection<Long> groupSetIds) {
        Example example = studyGroupUserDao.getExample();
        example.createCriteria()
                .andIn("studyGroupSetId", groupSetIds);
        return studyGroupUserDao.delete(example);
    }

    public int deleteGroupUserByGroupIds(Collection<Long> groupIds) {
        Example example = studyGroupUserDao.getExample();
        example.createCriteria()
                .andIn("studyGroupId", groupIds);
        return studyGroupUserDao.delete(example);
    }
}

package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AnnounceExtMapper;
import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.lms.core.base.vo.AnnounceVo;
import com.wdcloud.lms.core.base.vo.DialogVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AnnounceDao extends CommonDao<Announce, Long> {
    @Autowired
    private AnnounceExtMapper announceExtMapper;
    @Autowired
    private SectionUserDao sectionUserDao;
    public AnnounceExtMapper ext() {
        return announceExtMapper;
    }

    /**
     * 查询课程通知数量
     *
     * @param courseIds
     * @return (courseId, announceNumber)
     */
    public Map<Long, Integer> findCourseAnnounceNumber(List<Long> courseIds) {
        if (ListUtils.isEmpty(courseIds)) {
            return new HashMap<>();
        }
        List<Map<String, Long>> courseAnnounceNumber = announceExtMapper.findCourseAnnounceNumber(courseIds);

        Map<Long, Integer> result = new HashMap<>(courseAnnounceNumber.size());
        for (Map<String, Long> stringLongMap : courseAnnounceNumber) {
            result.put(stringLongMap.get("courseId"), stringLongMap.get("announceNumber").intValue());
        }
        return result;
    }

    @Override
    protected Class<Announce> getBeanClass() {
        return Announce.class;
    }


    public List<AnnounceVo> searchByTeacher(Map<String, String> param) {
        if (param.get("studyGroupId") == null) {//课程公告
            //课程公告列表 有分配
            return announceExtMapper.searchCourseAnnounceByTeacher(param);
        }else{//小组公告列表 无分配
            return announceExtMapper.searchGroupAnnounceByTeacher(param);
        }
    }

    public List<AnnounceVo> searchCourseAnnounceByStudent(Map<String, String> param) {
        return announceExtMapper.searchCourseAnnounceByStudent(param);
    }

    public List<AnnounceVo>  searchGroupAnnounceByStudent(Map<String, String> param) {
        return announceExtMapper.searchGroupAnnounceByStudent(param);
    }

    public List<DialogVo> findMessages(Long courseId, Long userId) {
        return announceExtMapper.findMessages(courseId, userId);
    }
}
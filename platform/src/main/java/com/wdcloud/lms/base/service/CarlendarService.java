package com.wdcloud.lms.base.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CarlendarService {
    @Autowired
    private CourseDao courseDao;
  /*  public List<Long> getStudentAssignIdsByCourseIds(Collection<String> courseIds, OriginTypeEnum originTypeEnum) {
        List<Assign> assigns = assignDao.getAssigns(courseIds, originTypeEnum);
        Set<Long> sectionIds = sectionUserDao.getSectionIdsByCourseIds(courseIds,WebContext.getUserId());
        List<Assign> associateAssigns = assignService.getAssociateAssign(sectionIds, assigns);
        return associateAssigns.stream().map(Assign::getId).collect(Collectors.toList());
    }*/

    public Map<String, List<String>> buildCalendarItemParam(String contextCodes) {
        //分三类（个人 课程 小组）  查询列表
        Iterable<String> contextCodesIterable = Splitter.on(",").omitEmptyStrings().trimResults().split(contextCodes);
        List<String> userIds = Lists
                .newArrayList(contextCodesIterable)
                .stream()
                .filter(o -> o.contains(CalendarTypeEnum.PERSON.getValue()+"_"))
                .map(o -> o.split(CalendarTypeEnum.PERSON.getValue()+"_")[1])
                .collect(Collectors.toList());
        List<String> courseIds = Lists
                .newArrayList(contextCodesIterable)
                .stream()
                .filter(o -> o.contains(CalendarTypeEnum.COURSE.getValue()+"_"))
                .map(o -> o.split(CalendarTypeEnum.COURSE.getValue()+"_")[1])
                .collect(Collectors.toList());
        List<String> studyGroupIds = Lists
                .newArrayList(contextCodesIterable)
                .stream()
                .filter(o -> o.contains(CalendarTypeEnum.STYDYGROUP.getValue()+"_"))
                .map(o -> o.split(CalendarTypeEnum.STYDYGROUP.getValue()+"_")[1])
                .collect(Collectors.toList());
        Map<String, List<String>> calendarTypeIdsMap = new HashMap<>();
        if (ListUtils.isNotEmpty(userIds)) {
            calendarTypeIdsMap.put(CalendarTypeEnum.PERSON.getValue().toString(),userIds);
        }
        if (ListUtils.isNotEmpty(courseIds)) {
            calendarTypeIdsMap.put(CalendarTypeEnum.COURSE.getValue().toString(),courseIds);
        }
        if (ListUtils.isNotEmpty(studyGroupIds)) {
            calendarTypeIdsMap.put(CalendarTypeEnum.STYDYGROUP.getValue().toString(),studyGroupIds);
        }
        return calendarTypeIdsMap;
    }

    public List<Long> prepareCourseIds(Map<String, Object> param) {
        List<Long> courseIds = new ArrayList<>();
        Long courseId = param.get("courseId")==null?null:Long.valueOf(param.get("courseId").toString());
        if (courseId == null||courseId==0) {//dashboard页 多个课程ID
            Map<String, Object> params = new HashMap<>();
//            params.put("status", Status.YES.getStatus());
            params.put("userId", WebContext.getUserId());
            params.put("roleId", WebContext.getRoleId());
            params.put("registryStatus", UserRegistryStatusEnum.JOINED.getStatus());
            List<CourseJoinedVo> courseJoinedVos = courseDao.findCourseJoined(params);
            courseIds = courseJoinedVos.stream()
                    .map(CourseJoinedVo::getId)
                    .collect(Collectors.toList());
          /*  //用户所有课程列表
            List<SectionUser> sectionUserList = sectionUserDao.find(SectionUser.builder()
                    .userId(WebContext.getUserId())
                    .roleId(WebContext.getRoleId())
                    .build());
            courseIds=sectionUserList.stream().map(SectionUser::getCourseId).map(a -> a.longValue()).distinct().collect(Collectors.toList());*/
        }else{//课程首页 单个课程ID
            courseIds.add(courseId);
        }
        return courseIds;
    }
}

package com.wdcloud.lms.business.calender;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.vo.CalendarDiscussionVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_DISCUSSION)
public class CalendarDicsussionDataQuery implements IDataQueryComponent<CalendarDiscussionVo> {

    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /calendarDiscussion/list 日历讨论列表
     * @apiName calendarDiscussionList
     * @apiGroup Calendar
     * @apiParam {String} contextCodes 日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123
     * @apiParam {String} [startDate] 开始日期 10位单位秒
     * @apiParam {String} [endDate] 结束日期 10位单位秒
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 讨论
     * @apiSuccess {Number} [entity.id] 讨论id
     * @apiSuccess {String} [entity.title] 标题
     * @apiSuccess {String} [entity.dueTime] 截止时间
     * @apiSuccess {String} [entity.sectionName] 班级名称
     * @apiSuccess {Number} [entity.type] 讨论类型 1: 普通讨论， 2： 小组讨论
     * @apiSuccess {Number} [entity.studyGroupSetId] 小组集ID
     * @apiSuccess {Number} [entity.ownStudyGroupId] 隶属小组ID
     * @apiSuccess {Number} [entity.assignTableId] 分配ID
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "entity": [
     * {
     * "id": 1,
     * "title": "2222",
     * "dueTime": 1545794105000,
     * "roleType":1
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends CalendarDiscussionVo> list(Map<String, String> param) {
        //只考虑 课程类型 2_开头
        Iterable<String> contextCodes = Splitter.on(",").omitEmptyStrings().trimResults().split(param.get("contextCodes"));
        List<String> courseIds = Lists
                .newArrayList(contextCodes)
                .stream()
                .filter(o -> o.startsWith(CalendarTypeEnum.COURSE.getValue()+"_"))
                .map(o -> o.split(CalendarTypeEnum.COURSE.getValue()+"_")[1])
                .collect(Collectors.toList());
        if (courseIds.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, Object> map = new HashMap<>();
        map.putAll(param);
        map.put("userId", WebContext.getUserId());
        map.put("courseIds", courseIds);
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_START_DATE)) || StringUtil.isNotEmpty(param.get(Constants.PARAM_END_DATE))) {
            map.put("hasDueTime", true);
        }
        List<CalendarDiscussionVo> voList = findCarlendarCourseDiscussionList(map);
        //按名称排序
        return voList.stream().sorted(Comparator.comparing(CalendarDiscussionVo::getTitle)).sorted().collect(Collectors.toList());
    }


    private List<CalendarDiscussionVo> findCarlendarCourseDiscussionList(Map<String, Object> map) {
            List<CalendarDiscussionVo> voList = new ArrayList<>();
            //老师
            if (!roleService.hasStudentRole()) {
                List<CalendarDiscussionVo> calendarDiscussionList = discussionDao.findCalendarDiscussionList(map);
                for (CalendarDiscussionVo vo : calendarDiscussionList) {
                    if (Objects.equals(AssignTypeEnum.USER.getType(), vo.getAssignType())) {
                        vo.setTitle(vo.getTitle() + "_" + vo.getUserName());
                    } else if (Objects.equals(AssignTypeEnum.SECTION.getType(), vo.getAssignType())) {
                        vo.setTitle(vo.getTitle() + "_" + vo.getSectionName());
                    }
                }
                voList.addAll(calendarDiscussionList);
            } else {//学生
                    List<CalendarDiscussionVo> calendarDiscussionVoList = discussionDao.findCalendarDiscussionListStudent(map);
                    for (CalendarDiscussionVo vo : calendarDiscussionVoList) {
                        if (Objects.equals(AssignTypeEnum.SECTION.getType(), vo.getAssignType())) {
                            vo.setTitle(vo.getTitle() + "_" + vo.getSectionName());
                        }
                    }
                    voList.addAll(calendarDiscussionVoList);
            }
            return voList;
    }


}

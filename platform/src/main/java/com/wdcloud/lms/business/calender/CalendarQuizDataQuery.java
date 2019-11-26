package com.wdcloud.lms.business.calender;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.vo.CalendarQuizVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_QUIZ)
public class CalendarQuizDataQuery implements IDataQueryComponent<CalendarQuizVo> {

    @Autowired
    private QuizDao quizDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /calendarQuiz/list 日历测验列表
     * @apiName calendarQuizList
     * @apiGroup Calendar
     * @apiParam {String} contextCodes 日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123
     * @apiParam {String} [startDate] 开始日期 10位单位秒
     * @apiParam {String} [endDate] 结束日期 10位单位秒
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 测验
     * @apiSuccess {Number} [entity.id] 测验id
     * @apiSuccess {String} [entity.title] 标题
     * @apiSuccess {String} [entity.dueTime] 截止时间
     * @apiSuccess {String} [entity.sectionName] 班级名称
     * @apiSuccess {String} [entity.assignTableId] 分配ID
     * @apiSuccess {Number} [entity.type] 测验类型 2：评分测验(graded quiz)、3：评分调查(graded survey)
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "entity": [
     * {
     * "id": 1,
     * "title": "2222",
     * "dueTime": 1545794105000
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends CalendarQuizVo> list(Map<String, String> param) {
        //只考虑 课程类型 2_开头
        Iterable<String> contextCodes = Splitter.on(",").omitEmptyStrings().trimResults().split(param.get("contextCodes"));
        List<String> courseIds = Lists
                .newArrayList(contextCodes)
                .stream()
                .filter(o -> o.contains(CalendarTypeEnum.COURSE.getValue()+"_"))
                .map(o -> o.split(CalendarTypeEnum.COURSE.getValue()+"_")[1])
                .collect(Collectors.toList());
        if (courseIds.isEmpty()) {
            return Lists.newArrayList();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", WebContext.getUserId());
        map.put("courseIds", courseIds);
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_START_DATE)) || StringUtil.isNotEmpty(param.get(Constants.PARAM_END_DATE))) {
            map.putAll(param);
            map.put("hasDueTime", true);
        }
        //测验
        List<CalendarQuizVo> voList =  findCarlendarCourseQuizList(map);
        //按名称排序
        return voList.stream().sorted(Comparator.comparing(CalendarQuizVo::getTitle)).sorted().collect(Collectors.toList());
    }


    private  List<CalendarQuizVo> findCarlendarCourseQuizList(Map<String, Object> map) {
            List<CalendarQuizVo> voList = new ArrayList<>();
            //老师
            if (!roleService.hasStudentRole()) {
                List<CalendarQuizVo> calendarQuizVoList = quizDao.findCalendarQuizList(map);
                for (CalendarQuizVo vo : calendarQuizVoList) {
                    if (Objects.equals(AssignTypeEnum.USER.getType(), vo.getAssignType())) {
                        vo.setTitle(vo.getTitle() + "_" + vo.getUserName());
                    } else if (Objects.equals(AssignTypeEnum.SECTION.getType(), vo.getAssignType())) {
                        vo.setTitle(vo.getTitle() + "_" + vo.getSectionName());
                    }
                }
                voList.addAll(calendarQuizVoList);
            } else {//学生
                    List<CalendarQuizVo> calendarQuizVoList = quizDao.findCalendarQuizListStudent(map);
                    for (CalendarQuizVo vo : calendarQuizVoList) {
                        if (Objects.equals(AssignTypeEnum.SECTION.getType(), vo.getAssignType())) {
                            vo.setTitle(vo.getTitle() + "_" + vo.getSectionName());
                        }
                    }
                    voList.addAll(calendarQuizVoList);
                }
                return voList;
            }


}

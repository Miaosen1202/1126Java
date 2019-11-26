package com.wdcloud.lms.business.syllabus;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.syllabus.vo.SyllabusListVO;
import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.EventDao;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.vo.EventVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SYLLABUS,
        functionName = Constants.FUNCTION_TYPE_LIST
)
public class SyllabusListQuery implements ISelfDefinedSearch<List<SyllabusListVO>> {

    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private EventDao eventDao;


    /**
     * @api {get} /syllabus/list/query 大纲列表查询
     * @apiName syllabusListQuery
     * @apiGroup Syllabus
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} [date] 日历选中的日期 yyyy/MM/dd
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 大纲列表
     * @apiSuccess {Number} entity.syllabusType 公共返回--大纲类型:1:任务;2:事件
     * @apiSuccess {String} entity.title 公共返回--标题
     * @apiSuccess {Number} entity.limitTime 公共返回--截止日期
     * @apiSuccess {Number} entity.isBule 公共返回--是否标蓝:1:标蓝;0:不标蓝
     * @apiSuccess {Number} entity.originType 公共返回--任务类型: 1: 作业 2：讨论 3: 测验 15: 事件
     *
     * @apiSuccess {Number} entity.originId 大纲类型为任务返回--任务ID
     *
     * @apiSuccess {Number} entity.eventId 大纲类型为事件返回--事件ID
     * @apiSuccess {Number} entity.startTime 大纲类型为事件返回--事件开始时间
     */
    @Override
    public List<SyllabusListVO> search(Map<String, String> condition) {
        //0、参数解析
        Long courseId = Long.parseLong(condition.get(Constants.PARAM_COURSE_ID));
        String oldDate = condition.get("date");
        String date = oldDate;
        if (null != oldDate) {
            String[] split = oldDate.split("/");
            if (split.length != 3) {
                throw new ParamErrorException();
            }
            String date2 = split[1];
            String date3 = split[2];
            if(date2.length() == 1) {
                date2 = "0" + date2;
            }
            if(date3.length() == 1) {
                date3 = "0" + date3;
            }
            date = split[0] + "/" + date2 + "/" + date3;
        }
        List<SyllabusListVO> list = new ArrayList<SyllabusListVO>();
        //1、获取所有任务
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PARAM_COURSE_ID, courseId);
        if (roleService.hasStudentRole()) {
            map.put(Constants.PARAM_STATUS, 1);
        }
        List<AssignmentGroupItem> assignments = assignmentGroupItemDao.findAssignmentGroupItemListByCourse(map);
        for (AssignmentGroupItem assignment: assignments) {
            SyllabusListVO vo = BeanUtil.beanCopyProperties(assignment, SyllabusListVO.class);
            vo.setSyllabusType(1);
            vo.setIsBule(0);
            //1.1、获取limitTime
            //1.2、排除学生端未分配给学生的任务
            if (roleService.hasStudentRole()) {
                AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), assignment.getOriginType(), assignment.getOriginId());
                if (null != assignUser && null != assignUser.getLimitTime()) {
                    vo.setLimitTime(assignUser.getLimitTime().getTime());
                }
                if (null != assignUser) {
                    list.add(vo);
                }
            } else {
                Example example2= assignDao.getExample();
                example2.orderBy(Assign.LIMIT_TIME);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo(Assign.COURSE_ID, courseId);
                criteria2.andEqualTo(Assign.ORIGIN_ID, assignment.getOriginId());
                criteria2.andEqualTo(Assign.ORIGIN_TYPE, assignment.getOriginType());
                List<Assign> assigns = assignDao.find(example2);
                if (assigns.size() > 0 && null != assigns.get(0).getLimitTime()) {
                    vo.setLimitTime(assigns.get(0).getLimitTime().getTime());
                }
                list.add(vo);
            }
        }
        //2、获取所有事件
        Map<String, String> param = new HashMap<>();
        param.put("calendarType",CalendarTypeEnum.COURSE.getValue().toString());
        param.put("courseIds", courseId.toString());
        List<EventVo> events=eventDao.findEventListByCourse(param);
        for (EventVo event: events) {
            SyllabusListVO vo = new SyllabusListVO();
            vo.setSyllabusType(2);
            vo.setIsBule(0);
            vo.setTitle(event.getTitle());
            if (null != event.getEndTime()) {
                vo.setLimitTime(event.getEndTime().getTime());
            }
            vo.setEventId(event.getId());
            if (null != event.getStartTime()) {
                vo.setStartTime(event.getStartTime().getTime());
            }
            vo.setOriginType(15);
            list.add(vo);
        }
        //3、获取isBule
        if (null != date) {
            for (SyllabusListVO vo: list) {
                if (null != vo.getLimitTime()) {
                    String limitTime = DateUtil.format(DateUtil.hoursOperation(new Date(vo.getLimitTime()), 8), "yyyy/MM/dd");
                    if (date.equals(limitTime)) {
                        vo.setIsBule(1);
                    }
                }
            }
        }
        //4、排序
        List<SyllabusListVO> newList = list.stream()
                .sorted((a, b) -> {
                    Long aLimitTime = a.getLimitTime();
                    Long bLimitTime = b.getLimitTime();
                    String aTitle = a.getTitle();
                    String bTitle = b.getTitle();
                    int result1 = 0;
                    int result2 = 0;
                    if (aLimitTime != null && bLimitTime != null) {
                        result1 = aLimitTime.compareTo(bLimitTime);
                    }
                    if (aLimitTime != null && bLimitTime == null) {
                        result1 = -1;
                    }
                    if (aLimitTime == null && bLimitTime != null) {
                        result1 = 1;
                    }
                    if (result1 != 0) {
                        result2 = result1;
                    } else {
                        if (aTitle != null && bTitle != null) {
                            result2 = aTitle.compareToIgnoreCase(bTitle);
                        }
                        if (aTitle != null && bTitle == null) {
                            result2 = -1;
                        }
                        if (aTitle == null && bTitle != null) {
                            result2 = 1;
                        }
                    }
                    return result2;
                }).collect(Collectors.toList());
        return newList;
    }
}

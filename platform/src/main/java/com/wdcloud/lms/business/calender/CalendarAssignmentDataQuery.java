package com.wdcloud.lms.business.calender;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.vo.AssignmentVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_RES)
public class CalendarAssignmentDataQuery implements IDataQueryComponent<Assignment> {


    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /calendarRes/list 日历作业列表
     * @apiName calendarResList
     * @apiGroup Calendar
     * @apiParam {String} contextCodes 日历项目 格式:日历类型_ID,日历类型_ID 逗号分隔 (日历类型, 1: 个人 2: 课程 3: 学习小组) 如 1_123,2_123
     * @apiParam {String} [startDate] 开始日期 10位单位秒
     * @apiParam {String} [endDate] 结束日期 10位单位秒
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} [entity.id] 作业id
     * @apiSuccess {String} [entity.title] 标题
     * @apiSuccess {String} [entity.dueTime] 截止时间
     * @apiSuccess {Number} [entity.roleType] 角色类型 2:老师 3:学生
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
    public List<? extends Assignment> list(Map<String, String> param) {
        //只考虑 课程类型 2_开头的
        Iterable<String> contextCodes = Splitter.on(",").omitEmptyStrings().trimResults().split(param.get("contextCodes"));
        List<String> courseIds = Lists
                .newArrayList(contextCodes)
                .stream()
                .filter(o -> o.contains("2_"))
                .map(o -> o.split("2_")[1])
                .collect(Collectors.toList());
        if (courseIds.isEmpty()) {
            return Lists.newArrayList();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", WebContext.getUserId());
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_START_DATE)) || StringUtil.isNotEmpty(param.get(Constants.PARAM_END_DATE))) {
            map.put(Constants.PARAM_START_DATE, param.get(Constants.PARAM_START_DATE));
            map.put(Constants.PARAM_END_DATE, param.get(Constants.PARAM_END_DATE));
            map.put("hasDueTime", true);
        }

        List<AssignmentVO> assignmentVOS = new ArrayList<>();
        map.put("courseIds", courseIds);
        //当前人在课程中的角色
        if (roleService.hasTeacherOrTutorRole()) {
            List<AssignmentVO> assignments = assignmentDao.findCalendarAssignmentList(map);
            for (AssignmentVO assignmentVO : assignments) {
                assignmentVO.setRoleType(roleService.getTeacherRoleId());
                if (Objects.equals(AssignTypeEnum.USER.getType(), assignmentVO.getAssignType())) {
                    assignmentVO.setTitle(assignmentVO.getTitle() + "_" + assignmentVO.getUserName());
                } else if (Objects.equals(AssignTypeEnum.SECTION.getType(), assignmentVO.getAssignType())) {
                    assignmentVO.setTitle(assignmentVO.getTitle() + "_" + assignmentVO.getSectionName());
                }
            }
            assignmentVOS.addAll(assignments);
        } else {//学生
                List<AssignmentVO> assignments = assignmentDao.findCalendarAssignmentListStudent(map);
                for (AssignmentVO assignmentVO : assignments) {
                    assignmentVO.setRoleType(roleService.getStudentRoleId());
                    if (Objects.equals(AssignTypeEnum.SECTION.getType(), assignmentVO.getAssignType())) {
                        assignmentVO.setTitle(assignmentVO.getTitle() + "_" + assignmentVO.getSectionName());
                    }
                }
                assignmentVOS.addAll(assignments);
        }
        //按名称排序
        return assignmentVOS.stream().sorted(Comparator.comparing(AssignmentVO::getTitle)).sorted().collect(Collectors.toList());
    }

}

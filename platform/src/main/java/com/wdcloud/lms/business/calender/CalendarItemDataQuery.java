package com.wdcloud.lms.business.calender;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.calender.enums.RegistryStatusEnum;
import com.wdcloud.lms.core.base.dao.CalendarUserCheckDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.vo.CalendarItemVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_ITEM)
public class CalendarItemDataQuery implements IDataQueryComponent<CalendarItemVo> {

    @Autowired
    private CalendarUserCheckDao calendarUserCheckDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SectionUserDao sectionUserDao;
    /**
     * @api {get} /calendarItem/list 日历项目列表
     * @apiName calendarItemList
     * @apiGroup Calendar
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Object[]} entity 列表
     * @apiSuccess {Number} entity.id 项目ID
     * @apiSuccess {Number=1,2,3} entity.calendarType 项目类型 1: 个人 2: 课程 3: 学习小组
     * @apiSuccess {String} entity.title 项目标题
     * @apiSuccess {Number} entity.coverColor 项目前景颜色
     * @apiSuccess {Number=0,1} entity.isCheck 是否选中  0:未选中 1:选中
     */
    @Override
    public List<? extends CalendarItemVo> list(Map<String, String> param) {
        Map<String,Object> params = new HashMap<>();
        params.putAll(param);
        params.put("userId", WebContext.getUserId());
        params.put("roleId", WebContext.getRoleId());
        params.put("isStudent",roleService.hasStudentRole());
        List<CalendarItemVo> calendarItemVoList = new ArrayList<>();
        //个人
        CalendarItemVo itemPerson=calendarUserCheckDao.findUserCalendarItem(params);
        if (itemPerson != null) {
            calendarItemVoList.add(itemPerson);
        }

        //课程
        List<SectionUser> sectionUserList = sectionUserDao.find(SectionUser.builder()
                .userId(WebContext.getUserId())
                .roleId(WebContext.getRoleId())
                .registryStatus(RegistryStatusEnum.JOINED.getValue())
                .build());
        List<Long> courseIds=sectionUserList.stream().map(SectionUser::getCourseId).distinct().collect(Collectors.toList());
        if (ListUtils.isNotEmpty(courseIds)) {
            params.put("courseIds",courseIds);
            List<CalendarItemVo> courseItemList=calendarUserCheckDao.findCourseCalendarItem(params);
            if (courseItemList != null) {
                calendarItemVoList.addAll(courseItemList);
            }

            //小组 仅学生显示
            if (roleService.hasStudentRole()) {
                List<CalendarItemVo> groupItemList=calendarUserCheckDao.findGroupCalendarItem(params);
                if (groupItemList != null) {
                    calendarItemVoList.addAll(groupItemList);
                }
            }
        }

        return calendarItemVoList;
    }
}

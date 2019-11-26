package com.wdcloud.lms.business.calender;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.calender.dto.UserTodoAddDTO;
import com.wdcloud.lms.core.base.enums.CalendarTypeEnum;
import com.wdcloud.lms.core.base.dao.UserTodoDao;
import com.wdcloud.lms.core.base.model.UserTodo;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SuppressWarnings("JavaDoc")
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_TODO)
public class UserTodoDataEdit implements IDataEditComponent {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserTodoDao userTodoDao;
    /**
     * @api {post} /calendarTodo/add 日历-待办创建
     * @apiName calendarTodoAdd
     * @apiGroup Calendar
     *
     * @apiParam {Number} calendarType 日历类型, 1: 个人 2: 课程
     * @apiParam {Number} [courseId]课程ID 所属课程(calendar_type=2时非空)
     * @apiParam {String} title 标题
     * @apiParam {String} [content] 详细内容
     * @apiParam {Number} doTime 执行时间
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @ValidationParam(clazz = UserTodoAddDTO.class,groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        UserTodoAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserTodoAddDTO.class);
        dto.setUserId(WebContext.getUserId());
        dto.setCreateUserId(WebContext.getUserId());
        dto.setUpdateUserId(WebContext.getUserId());
        verifyPerm(dto);
        userTodoDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * 增加 编辑 删除
     * @param dto
     */
    private void verifyPerm(UserTodoAddDTO dto) {
        if (CalendarTypeEnum.COURSE.getValue().equals(dto.getCalendarType())) {
            boolean teacherOrTutorRole = roleService.hasTeacherOrTutorRole(dto.getCourseId());
            if (teacherOrTutorRole) {
                log.info("教师角色不能操作日历待办事项！");
                throw new PermissionException();
            }
        }

    }

    /**
     * @api {post} /calendarTodo/modify 日历-待办编辑
     * @apiName calendarTodoModify
     * @apiGroup Calendar
     *
     * @apiParam {Number} id 待办ID
     * @apiParam {Number} calendarType 日历类型, 1: 个人 2: 课程
     * @apiParam {Number} [courseId]课程ID 所属课程(calendar_type=2时非空)
     * @apiParam {String} title 标题
     * @apiParam {String} [content] 详细内容
     * @apiParam {Number} [doTime] 执行时间
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @ValidationParam(clazz = UserTodoAddDTO.class,groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        UserTodoAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserTodoAddDTO.class);
        UserTodo userTodo=userTodoDao.findOne(UserTodo.builder().id(dto.getId()).build());
        if (userTodo == null) {
            log.info("记录为空！");
            throw new ParamErrorException();
        }
        verifyPerm(dto);
        userTodoDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /calendarTodo/deletes 日历待办删除
     * @apiName calendarTodoDeletes
     * @apiGroup Calendar
     *
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除成功的记录条数
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "1"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        int count=userTodoDao.deletes(idList);
        return new LinkedInfo(count+"");
    }
}

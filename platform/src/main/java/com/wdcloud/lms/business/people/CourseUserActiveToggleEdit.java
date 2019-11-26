package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.people.vo.CourseUserActiveToggleVo;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_ACTIVE_TOGGLE
)
public class CourseUserActiveToggleEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {post} /courseUser/activeToggle/edit 用户启用,禁用操作
     * @apiName courseUserActiveToggle
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     * @apiParam {Number=0,1} activeStatus 激活状态
     */
    @ValidationParam(clazz = CourseUserActiveToggleVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserActiveToggleVo userActiveToggleVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserActiveToggleVo.class);
        if (Objects.equals(WebContext.getUserId(), userActiveToggleVo.getUserId())) {
            throw new PermissionException();
        }

        courseUserDao.updateActiveStatus(userActiveToggleVo.getCourseId(), userActiveToggleVo.getUserId(), Status.statusOf(userActiveToggleVo.getActiveStatus()));
        return new LinkedInfo(String.valueOf(userActiveToggleVo.getUserId()));
    }
}

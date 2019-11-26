package com.wdcloud.lms.business.user;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_TEACHER_USER)
public class TeacherUserQuery implements IDataQueryComponent<User> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /teacherUser/list 教师用户列表
     * @apiDescription 通过courseId查找该课程下的所有老师用户
     * @apiName teacherUserList
     * @apiGroup People
     *
     * @apiParam {String} courseId 课程ID
     * @apiSuccess {String} code
     * @apiSuccess {String} [message]
     * @apiSuccess {Object[]} [entity] 用户列表
     * @apiSuccess {Number} entity.id 用户ID
     * @apiSuccess {String} entity.username 用户名
     * @apiSuccess {String} entity.fullName 全名
     */
    @Override
    public List<? extends User> list(Map<String, String> param) {
        List<User> teacherUsers = userDao.findUserListByRole(Long.parseLong(param.get(Constants.PARAM_COURSE_ID)), RoleEnum.TEACHER.getType());
        return teacherUsers;
    }
}
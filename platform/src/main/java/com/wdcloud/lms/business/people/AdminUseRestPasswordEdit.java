package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ADMIN_USER,
        functionName = Constants.FUNCTION_TYPE_REST_PASSWORD
)
public class AdminUseRestPasswordEdit implements ISelfDefinedEdit {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService service;

    /**
     * @api {post} /adminUser/restPassword/edit 重置密码
     * @apiDescription 重置密码
     * @apiName 重置密码
     * @apiGroup admin
     * @apiParam {Number} id id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        if (service.isAdmin()) {
            Long uid = JSON.parseObject(dataEditInfo.beanJson).getLong(Constants.PARAM_ID);
            User user = userDao.get(uid);
            user.setPassword(PasswordUtil.haxPassword(Constants.USER_DEFAULT_PASSWORD));
            userDao.updateIncludeNull(user);
        }
        return new LinkedInfo(Code.OK.name);
    }
}

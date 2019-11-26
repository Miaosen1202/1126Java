package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@ResourceInfo(name = Constants.RESOURCE_TYPE_LOGIN)
public class LoginDataQuery implements IDataQueryComponent<Object> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /login/get 角色列表
     * @apiName RoleList
     * @apiGroup Common
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {Object[]} entity 角色列表
     * @apiSuccess {Number} entity.id 角色ID
     * @apiSuccess {String} entity.name 角色名称
     */
    @Override
    public Object find(String data) {

        return null;
    }
}

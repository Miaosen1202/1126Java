package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ORG_ADMIN)
public class OrgAdminDataQuery implements IDataQueryComponent<User> {

    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /orgAdmin/list 机构管理员列表(含子机构)
     * @apiDescription 机构管理员列表
     * @apiName orgAdminList
     * @apiGroup admin
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} [entity.sisId] SIS ID
     * @apiSuccess {Number} entity.username 登录名
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} [entity.fullname] 用户全名
     */
    @Override
    public List<? extends User> list(Map<String, String> param) {
        return userDao.getOrgAdminIncludeChild(WebContext.getOrgTreeId());
    }

}

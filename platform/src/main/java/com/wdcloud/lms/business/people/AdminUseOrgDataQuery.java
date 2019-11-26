package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ADMIN_USER_ORG)
public class AdminUseOrgDataQuery implements IDataQueryComponent<Org> {

    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /adminUserOrg/list 管理员用户查询
     * @apiDescription 管理员用户查询
     * @apiName adminUserOrgList
     * @apiGroup admin
     * @apiParam {Number} userId 用户ID
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} [entity.sisId] SIS ID
     * @apiSuccess {Number} entity.username 登录名
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} [entity.fullname] 用户全名
     */
    @Override
    public List<? extends Org> list(Map<String, String> param) {
        return orgDao.findUserAdminOrg(Long.valueOf(param.get(Constants.PARAM_USER_ID)));
    }

}

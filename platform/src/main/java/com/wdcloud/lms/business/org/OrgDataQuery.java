package com.wdcloud.lms.business.org;

import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ORG)
public class OrgDataQuery implements IDataQueryComponent<Org> {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /org/list 机构列表
     * @apiName OrgList
     * @apiGroup Org
     * @apiParam {String} [parentId] 上级ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 机构列表
     */
    @Override
    public List<? extends Org> list(Map<String, String> param) {
        String parentId = param.get(Org.PARENT_ID);
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtil.isEmpty(parentId)) {//为空为第一次查询
            //获取当前登录用户的组织机构
            //查询子机构数量和机构下面包含的课程数量
            map.put(Org.ID, WebContext.getOrgId());
        } else {
            map.put(Org.PARENT_ID, parentId);
        }
        return orgDao.getOrgWithSubCountAndCoursesCount(map);

    }
}

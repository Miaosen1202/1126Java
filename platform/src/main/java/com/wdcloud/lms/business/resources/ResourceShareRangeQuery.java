package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.ResourceShareSettingsDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.ResourceShareSetting;
import com.wdcloud.lms.core.base.vo.resource.ResourceShareSettingVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES_SHARE_SETTINGS,
        functionName = Constants.FUNCTION_TYPE_SHARE_RANGE
)
public class ResourceShareRangeQuery implements ISelfDefinedSearch<ResourceShareSettingVO> {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private ResourceShareSettingsDao resourceShareSettingsDao;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resShareSettings/shareRange/query 查询管理员设置的资源分享范围
     * @apiDescription 查询管理员设置的资源分享范围
     * @apiName resShareRangeQuery
     * @apiGroup resource
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 响应体
     * @apiSuccess {Object} entity.id 资源
     * @apiSuccess {Number} entity.shareRange 分享范围，2：机构，3：公开
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "id": 1,
     *         "shareRange": 2
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public ResourceShareSettingVO search(Map<String, String> condition) {
        roleValidateService.teacherAndAdminValid();

        String orgTreeId = WebContext.getOrgTreeId();
        orgTreeId = orgTreeId.substring(0, Constants.TREE_ROOT_TREE_ID_LENGTH);
        Org rootOrg = orgDao.getByTreeId(orgTreeId);

        ResourceShareSetting shareSetting = resourceShareSettingsDao.findByOrgId(rootOrg.getId());
        ResourceShareSettingVO shareSettingVO = BeanUtil.copyProperties(shareSetting, ResourceShareSettingVO.class);

        return shareSettingVO;
    }
}

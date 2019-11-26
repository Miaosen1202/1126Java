package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.dto.ResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.ResourceShareSettingDTO;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.ResourceShareSettingsDao;
import com.wdcloud.lms.core.base.model.ResourceShareSetting;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author  WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_SHARE_SETTINGS, description = "资源分享设置")
public class ResourceShareSettingsEdit implements IDataEditComponent {

    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceShareSettingsDao resourceShareSettingsDao;

    /**
     * @api {post} /resShareSettings/modify 资源分享设置
     * @apiDescription 资源分享设置
     * @apiName resShareSettingsAdd
     * @apiGroup resource
     *
     * @apiParam {Number=2,3} shareRange 分享范围，2：机构，3：公开
     * @apiExample {json} 请求示例:
     * {
     *     "shareRange":2
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {Object} [entity] 资源信息
     * @apiSuccessExample {json} 响应示例：
     * {
     *      "code": 200,
     *      "message": "2"
     * }
     */
    @ValidationParam(clazz = ResourceShareSettingDTO.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        roleValidateService.onlyAdminValid();

        ResourceShareSettingDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceShareSettingDTO.class);
        ResourceShareSetting resourceShareSettingCondition = ResourceShareSetting.builder()
                .orgId(WebContext.getOrgId()).build();

        ResourceShareSetting resourceShareSetting =  resourceShareSettingsDao.findOne(resourceShareSettingCondition);
        if(Objects.isNull(resourceShareSetting)){
            resourceShareSettingCondition.setShareRange(dto.getShareRange());
            resourceShareSettingsDao.save(resourceShareSettingCondition);
            return new LinkedInfo(String.valueOf(resourceShareSettingCondition.getId()));
        }else{
            resourceShareSetting.setShareRange(dto.getShareRange());
            resourceShareSettingsDao.update(resourceShareSetting);
            return new LinkedInfo(String.valueOf(resourceShareSetting.getId()));
        }
    }
}

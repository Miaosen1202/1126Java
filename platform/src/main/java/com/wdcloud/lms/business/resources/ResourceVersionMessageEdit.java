package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.dto.ResourceVersionMessageDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.ResourceVersionMessageDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceVersionMessage;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_VERSION_IGNORE, description = "资源版本忽略")
public class ResourceVersionMessageEdit implements IDataEditComponent {

    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {post} /resVersionIgnore/modify 忽略当前最新资源版本的提醒
     * @apiName resVersionIgnoreModify 忽略当前最新资源版本的提醒
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源id
     * @apiExample {json} 请求示例:
     * {
     * 	    "resourceId":4
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 课程ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "2"
     * }
     */
    @AccessLimit
    @ValidationParam(clazz = ResourceVersionMessageDTO.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();
        ResourceVersionMessageDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceVersionMessageDTO.class);

        ResourceVersionMessage resourceVersionMessage = ResourceVersionMessage.builder()
                .resourceId(dto.getResourceId()).userId(WebContext.getUserId()).build();
        resourceVersionMessage = resourceVersionMessageDao.findOne(resourceVersionMessage);
        resourceVersionMessage.setIsRemind(Status.NO.getStatus());
        resourceVersionMessageDao.update(resourceVersionMessage);

        return new LinkedInfo(String.valueOf(resourceVersionMessage.getId()));
    }
}

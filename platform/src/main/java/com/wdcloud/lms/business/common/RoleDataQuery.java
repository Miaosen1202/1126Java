package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Data
//@ResourceInfo(name = Constants.RESOURCE_TYPE_ROLE)
public class RoleDataQuery implements IDataQueryComponent {
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /role/list 角色列表
     * @apiName RoleList
     * @apiGroup Common
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {Object[]} entity 角色列表
     * @apiSuccess {Number} entity.id 角色ID
     * @apiSuccess {String} entity.name 角色名称
     */
    @Override
    public List list(Map param) {
        return roleService.findAllRoles();
    }
}

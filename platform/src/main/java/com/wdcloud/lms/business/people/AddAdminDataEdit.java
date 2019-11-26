package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.people.dto.AddAdminDTO;
import com.wdcloud.lms.core.base.dao.OrgUserDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.OrgUser;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@ResourceInfo(name = Constants.RESOURCE_TYPE_AddADMIN)
public class AddAdminDataEdit implements IDataEditComponent {
    @Autowired
    private OrgUserDao orgUserDao;


    /**
     * @api {get} /addAdmin/add 添加管理员
     * @apiDescription 添加管理员
     * @apiName addAdminAdd
     * @apiGroup admin
     * @apiParam {String} orgId 机构ID
     * @apiParam {String} orgTreeId 机构树
     * @apiParam {String[]} users 机构树
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {

        AddAdminDTO dto = JSON.parseObject(dataEditInfo.beanJson, AddAdminDTO.class);
        dto.getUsers().forEach(o -> {
            OrgUser user = OrgUser.builder()
                    .userId(o.getId())
                    .orgId(dto.getOrgId())
                    .orgTreeId(dto.getOrgTreeId())
                    .roleId(RoleEnum.ADMIN.getType())
                    .build();
            if (orgUserDao.find(user).isEmpty()) {
                orgUserDao.save(user);
            }
        });
        return new LinkedInfo(Code.OK.name);
    }
}

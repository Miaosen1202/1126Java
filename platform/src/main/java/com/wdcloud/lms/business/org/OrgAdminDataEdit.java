package com.wdcloud.lms.business.org;

import com.alibaba.fastjson.JSONObject;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.org.dto.OrgUserDTO;
import com.wdcloud.lms.core.base.dao.OrgUserDao;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ORG_ADMIN,
        functionName = Constants.FUNCTION_TYPE_REMOVE
)
public class OrgAdminDataEdit implements ISelfDefinedEdit {

    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private IRedisService redisService;

    /**
     * @api {get} /orgAdmin/remove/edit 机构管理删除
     * @apiName orgAdminRemove
     * @apiGroup Org
     * @apiParam {Number} orgUserId 机构用户id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * {
     * "code": 200,
     * "message": "common.success"
     * }
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        OrgUserDTO dto = JSONObject.parseObject(dataEditInfo.beanJson, OrgUserDTO.class);
        orgUserDao.delete(dto.getOrgUserId());
        redisService.del(Constants.REDIS_ADMIN_LAST_LOGIN_ORG + dto.getOrgUserId());
        return new LinkedInfo(Code.OK.name);
    }
}

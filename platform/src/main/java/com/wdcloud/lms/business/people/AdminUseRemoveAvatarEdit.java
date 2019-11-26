package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ADMIN_USER,
        functionName = Constants.FUNCTION_TYPE_REMOVE_AVATAR
)
public class AdminUseRemoveAvatarEdit implements ISelfDefinedEdit {
    @Autowired
    private UserDao userDao;

    /**
     * @api {post} /adminUser/removeAvatar/edit 清除用户头像
     * @apiDescription 清除用户头像
     * @apiName 清除用户头像
     * @apiGroup admin
     * @apiParam {Number} id id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Long uid = JSON.parseObject(dataEditInfo.beanJson).getLong(Constants.PARAM_ID);
        User user = userDao.get(uid);
        user.setAvatarFileId(null);
        userDao.updateIncludeNull(user);
        return new LinkedInfo(Code.OK.name);
    }
}

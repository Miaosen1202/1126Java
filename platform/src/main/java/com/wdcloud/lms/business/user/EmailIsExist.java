package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserEmailDao;
import com.wdcloud.lms.core.base.model.UserEmail;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能：用户关联邮件地址是否已经填加过
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_SETTING,
        functionName = Constants.FUNCTION_TYPE_EXIST
)
public class EmailIsExist implements ISelfDefinedEdit {
    @Autowired
    private UserEmailDao userEmailDao;


    /**
     * @api {post} /userSetting/exist/edit 邮件地址是否存在
     * @apiName emailIsExist
     * @apiGroup user
     * @apiParam {String} [email] 用户关联邮件
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} status  0:表示不存在；1或者大于1的值：表示存在；
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {

        final UserEmail dto = JSON.parseObject(dataEditInfo.beanJson, UserEmail.class);

         int openstatus = userEmailDao.getEmailIsExist(dto.getEmail());

        return new LinkedInfo("status is:" + String.valueOf(openstatus));
    }
}

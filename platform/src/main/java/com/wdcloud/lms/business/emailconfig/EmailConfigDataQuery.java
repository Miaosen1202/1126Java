package com.wdcloud.lms.business.emailconfig;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.OrgEmailDao;
import com.wdcloud.lms.core.base.model.OrgEmail;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_EMAIL_CONFIG)
public class EmailConfigDataQuery implements IDataQueryComponent<OrgEmail> {

    @Autowired
    private OrgEmailDao orgEmailDao;


    /**
     * @api {get} /emailConfig/get 邮件配置详情
     * @apiName emailConfigGet
     * @apiGroup emailConfig
     *
     * @apiParam {Number} data 用户ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.isOpen 是否开通
     * @apiSuccess {Number=0,1} entity.isOpenRegist 是否开通注册 0：否 1: 是
     * @apiSuccess {Number=0,1} entity.isOpenRetrievePwd 是否开通找回密码 0：否 1: 是
     * @apiSuccess {Number=0,1} entity.isOpenPushNotify 是否开通邮件通知 0：否 1: 是
     * @apiSuccess {String} entity.emailDisplayName 邮件默认名称
     * @apiSuccess {String} entity.host 邮件Host
     * @apiSuccess {Number} entity.port 端口
     * @apiSuccess {Number} entity.security 加密类型 0:none,1:ssl,2:tls
     * @apiSuccess {Number} entity.emaiAuthType 授权类型 0:login
     * @apiSuccess {String} entity.emailUserName 邮箱用户名
     * @apiSuccess {String} entity.emailPwd 邮箱授权码
     *
     */
    @Override
    public OrgEmail find(String id) {
        String code= WebContext.getUser().getOrgTreeId().substring(0,8);
        return orgEmailDao.findOne(OrgEmail.builder().rootOrgCode(code).build());
    }
}

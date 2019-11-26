package com.wdcloud.lms.business.emailconfig;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.OrgEmailDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.OrgEmail;
import com.wdcloud.lms.util.MailService;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_EMAIL_CONFIG)
public class EmailConfigDataEdit implements IDataEditComponent {

    @Autowired
    private OrgEmailDao orgEmailDao;
    @Autowired
    private MailService mailService;
    /**
     * @api {post} /emailConfig/add 邮件配置创建
     * @apiName emailConfigAdd 邮件配置创建
     * @apiGroup emailConfig
     *
     * @apiParam {Number=0,1} isOpen 是否开通 0：否 1: 是
     * @apiParam {Number=0,1} isOpenRegist 是否开通注册 0：否 1: 是
     * @apiParam {Number=0,1} isOpenRetrievePwd 是否开通找回密码 0：否 1: 是
     * @apiParam {Number=0,1} isOpenPushNotify 是否开通邮件通知 0：否 1: 是
     * @apiParam {String} emailDisplayName 邮件默认名称
     * @apiParam {String} emailHost 邮件Host
     * @apiParam {Number} emaiPort 端口
     * @apiParam {Number} emaiSecurityType 加密类型 0:none,1:ssl,2:tls
     * @apiParam {Number} emaiAuthType 授权类型 0:login
     * @apiParam {String} emailUserName 邮箱用户名
     * @apiParam {String} emailPwd 邮箱授权码
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = OrgEmail.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        OrgEmail dto = JSON.parseObject(dataEditInfo.beanJson, OrgEmail.class);
        dto.setRootOrgCode(WebContext.getUser().getOrgTreeId().substring(0,8));
        orgEmailDao.save(dto);
        mailService.initJavaMailSenderImplMap();
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /emailConfig/modify 邮件配置修改
     * @apiName emailConfigModify 邮件配置修改
     * @apiGroup emailConfig
     *
     * @apiParam {Number} id  ID
     * @apiParam {Number=0,1} isOpen 是否开通 0：否 1: 是
     * @apiParam {Number=0,1} isOpenRegist 是否开通注册 0：否 1: 是
     * @apiParam {Number=0,1} isOpenRetrievePwd 是否开通找回密码 0：否 1: 是
     * @apiParam {Number=0,1} isOpenPushNotify 是否开通邮件通知 0：否 1: 是
     * @apiParam {String} emailDisplayName 邮件默认名称
     * @apiParam {String} host 邮件Host
     * @apiParam {Number} port 端口
     * @apiParam {Number} security 加密类型 0:none,1:ssl,2:tls
     * @apiParam {Number} emaiAuthType 授权类型 0:login
     * @apiParam {String} emailUserName 邮箱用户名
     * @apiParam {String} emailPwd 邮箱授权码
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @ValidationParam(clazz = OrgEmail.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        OrgEmail dto = JSON.parseObject(dataEditInfo.beanJson, OrgEmail.class);
        orgEmailDao.update(dto);
        mailService.initJavaMailSenderImplMap();
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

}

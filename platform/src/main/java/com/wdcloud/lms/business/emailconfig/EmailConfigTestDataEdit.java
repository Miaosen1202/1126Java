package com.wdcloud.lms.business.emailconfig;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.emailconfig.dto.EmailConfigTestDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.OrgEmailDao;
import com.wdcloud.lms.core.base.model.OrgEmail;
import com.wdcloud.lms.exceptions.ResourceNotFoundException;
import com.wdcloud.lms.util.MailService;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_EMAIL_CONFIG_TEST)
public class EmailConfigTestDataEdit implements IDataEditComponent {

    @Autowired
    private OrgEmailDao orgEmailDao;
    @Autowired
    private MailService mailService;
    /**
     * @api {post} /emailConfigTest/add 邮件配置测试
     * @apiName emailConfigTestAdd 邮件配置测试
     * @apiGroup emailConfig
     *
     * @apiParam {Number=0,1} testType 测试类型 0：Test Configuration 按钮 1：Mail Send 页面
     * @apiParam {String} [recEmail] 收件箱
     * @apiParam {String} [subject] 邮件主题
     * @apiParam {String} [content] 邮件内容
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = EmailConfigTestDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo){
        EmailConfigTestDTO dto = JSON.parseObject(dataEditInfo.beanJson, EmailConfigTestDTO.class);
        if (dto.getTestType().equals(0)) {
            String code=WebContext.getUser().getOrgTreeId().substring(0,8);
            OrgEmail orgEmail= orgEmailDao.findOne(OrgEmail.builder().rootOrgCode(code).build());
            if (orgEmail != null) {
                mailService.sendSimpleEmail(orgEmail.getEmailUserName(),"邮件服务器配置","配置成功！");
            }else{
                throw new ResourceNotFoundException();
            }
        }else{
            mailService.sendHtmlEmail(dto.getRecEmail(),dto.getSubject(),dto.getContent());
        }
        return new LinkedInfo(String.valueOf(1));
    }


}

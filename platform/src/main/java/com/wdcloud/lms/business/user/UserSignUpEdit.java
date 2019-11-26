package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.PasswordUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER,
        functionName = Constants.FUNCTION_TYPE_SIGN_UP
)
public class UserSignUpEdit implements ISelfDefinedEdit {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserConfigDao userConfigDao;

    /**
     * @api {post} /user/signUp/edit 用户注册
     * @apiName UserRegistry
     * @apiGroup User
     * @apiParam {String} username 登录名
     * @apiParam {String} password 密码
     * @apiParam {Number} orgId 机构ID
     * @apiParam {String} firstName First name
     * @apiParam {String} lastName Last name
     * @apiParam {String} [phone] 联系方式
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 新用户ID
     */
    @ValidationParam(clazz = UserSignUpVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserSignUpVo signUpVo = JSON.parseObject(dataEditInfo.beanJson, UserSignUpVo.class);
        Org org = orgDao.get(signUpVo.getOrgId());
        if (org == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_ORG_ID, signUpVo.getOrgId());
        }

        if (userDao.existsUsername(signUpVo.getUsername())) {
            throw new BaseException("prop.value.exists2", Constants.PARAM_USER_NAME, signUpVo.getUsername());
        }

        User user = BeanUtil.beanCopyProperties(signUpVo, User.class);
        user.setPassword(PasswordUtil.haxPassword(signUpVo.getPassword()));
        user.setFullName(signUpVo.getFirstName() + " " + signUpVo.getLastName());
        user.setOrgTreeId(org.getTreeId());
        userDao.save(user);

        userConfigDao.save(UserConfig.builder()
                .userId(user.getId())
                .build());

        userFileService.initPersonalSpaceDir(user.getId());

        return new LinkedInfo(String.valueOf(user.getId()));
    }

    @Data
    public static class UserSignUpVo {
        @NotEmpty
        @Size(max = 100)
        private String username;
        @NotEmpty
        @Size(max = 60, min = 6)
        private String password;
        @NotNull
        private Long orgId;
        @NotEmpty
        @Size(max = 60)
        private String firstName;
        @NotEmpty
        @Size(max = 50)
        private String lastName;
        @Size(max = 30)
        private String phone;
    }
}

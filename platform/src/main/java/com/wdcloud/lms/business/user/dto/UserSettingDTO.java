package com.wdcloud.lms.business.user.dto;

import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserLink;
import lombok.Data;

import java.util.List;
@Data
public class UserSettingDTO extends User {

    /**
     * 登录旧密码
     */
    private String oldPassword;
    /**
     * 用户介绍链接
     */
    private  List<UserLink> links;
}

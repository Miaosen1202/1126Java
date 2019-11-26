package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserEmail;
import com.wdcloud.lms.core.base.model.UserLink;
import lombok.Data;

import java.util.List;

@Data
public class UserSettingVo extends User {
    /**
     * 用户介绍链接
     */
    private  List<UserLink> links;
    /**
     * 用户介绍链接
     */
    private  List<UserEmail> emails;

}

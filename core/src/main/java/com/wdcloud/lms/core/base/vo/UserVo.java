package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.User;
import lombok.Data;

@Data
public class UserVo extends User {
    public String avatarName;
    public String avatarUrl;
}

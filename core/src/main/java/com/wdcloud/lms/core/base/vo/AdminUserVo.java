package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.User;
import lombok.Data;

@Data
public class AdminUserVo extends User {
    public String orgUserId;
    public String userId;

    @Override
    public String getPassword() {
        return super.getPassword();
    }
}

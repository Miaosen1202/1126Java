package com.wdcloud.lms.business.people.vo;

import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.AdminUserDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailVO extends User {
    private List<AdminUserDetailVO> courses;

    @Override
    public String getPassword() {
        return null;
    }
}

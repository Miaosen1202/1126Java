package com.wdcloud.lms.api.vo;

import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.User;
import lombok.Data;

import java.util.List;

@Data
public class LoginVO extends User {
    private Long type;

    private List<Org> orgs;

    @Override
    public String getPassword() {
        return null;
    }
}

package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.SectionUser;
import lombok.Data;

import java.util.List;

@Data
public class AdminUserDetailVO {
    private Long id;
    private String name;
    private Integer isActive;
    private List<SectionUser> roles;
}

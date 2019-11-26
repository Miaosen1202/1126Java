package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseUserDetailVo {
    private Long userId;
    private String sisId;
    private String nickname;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer status;
    private Integer isActive;
    private String email;
    private Date lastActivity;
    private Integer totalActivity;
    private String avatarFileUrl;

    List<SectionUserDetailVo> sectionUserDetailVos;
}



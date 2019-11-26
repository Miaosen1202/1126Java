package com.wdcloud.lms.business.studygroup.vo;

import com.wdcloud.lms.core.base.model.StudyGroupUser;
import lombok.Data;


@Data
public class StudyGroupUserVo extends StudyGroupUser {
    private String username;
    private String nickname;
    private String avatarFileUrl;
}

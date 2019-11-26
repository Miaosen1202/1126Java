package com.wdcloud.lms.business.file.vo;

import com.wdcloud.lms.core.base.model.UserFile;
import lombok.Data;

@Data
public class UserFileVo extends UserFile {
    private String createUserName;
    private String updateUserName;
}

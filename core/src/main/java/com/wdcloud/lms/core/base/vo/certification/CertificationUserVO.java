package com.wdcloud.lms.core.base.vo.certification;

import com.wdcloud.lms.core.base.model.CertificationUser;
import lombok.Data;

@Data
public class CertificationUserVO extends CertificationUser {
    //用户名
    private String username;
    //用户fullname
    private String fullName;

    //附件URL
    private String fileUrl;
    //附件名称
    private String fileName;
    //显示状态
    private Integer showStatus;
}

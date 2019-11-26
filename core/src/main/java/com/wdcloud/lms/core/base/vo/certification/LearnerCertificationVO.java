package com.wdcloud.lms.core.base.vo.certification;

import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.model.UserFile;
import lombok.Data;

import java.util.Date;

@Data
public class LearnerCertificationVO extends CertificationUser {
    /**
     * 认证名称
     */
    private String name;

    /**
     * 操作截止天数
     */
    private Integer opDay;

    /**
     * 认证类型 0：永久 1: 周期
     */
    private Integer type;

    /**
     * 证件有效月份（单位月）
     */
    private Integer validity;

    /**
     * 发行机构 0：Internal 1：External
     */
    private Integer issuer;
    /**
     * 截止日期
     */
    private Date dueTime;

    //认证主体
    private Long coverImgId;
    private String memo;
    private String fileUrl;
    private Integer certStatus;

    private UserFile userFile;
    private UserFile proofFile;
}

package com.wdcloud.lms.business.certification.dto;

import com.wdcloud.lms.core.base.model.CertificationUser;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CertificationUserModifyDTO extends CertificationUser {
    /**
     * 认证用户关联ID
     */
    @NotNull
    private Long id;
    /**
     * 操作类型 1:Mark Complete 2：Reject 3：Unenroll 4：ReEnroll
     */
    @NotNull
    private Integer opType;
}

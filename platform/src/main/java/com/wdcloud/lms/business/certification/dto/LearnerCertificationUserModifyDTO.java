package com.wdcloud.lms.business.certification.dto;

import com.wdcloud.lms.core.base.model.CertificationUser;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author wangff
 * @date 2019/8/9 14:29
 */
@Data
public class LearnerCertificationUserModifyDTO{
    @NotEmpty
    private Long certificationId;
    @NotBlank
    private String fileUrl;
}

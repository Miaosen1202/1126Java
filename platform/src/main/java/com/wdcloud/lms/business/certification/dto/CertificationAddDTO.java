package com.wdcloud.lms.business.certification.dto;

import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.validate.groups.GroupAdd;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CertificationAddDTO extends Certification {
    @NotBlank(groups = { GroupAdd.class})
    private String name;
    private String fileUrl;

}

package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ResourceImportDTO {

    @NotBlank
    private Long resourceId;

    @NotBlank
    private List<Long> courseIds;
}

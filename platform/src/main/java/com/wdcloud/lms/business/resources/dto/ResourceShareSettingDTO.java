package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResourceShareSettingDTO {

    @NotNull
    private Integer shareRange;
}

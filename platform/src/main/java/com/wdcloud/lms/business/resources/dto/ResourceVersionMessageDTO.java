package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResourceVersionMessageDTO {

    @NotNull
    private Long resourceId;
}

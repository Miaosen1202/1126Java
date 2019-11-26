package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ResourceImportUpdateDTO {

    @NotNull
    private Long resourceId;

    @NotNull
    private List<Long> courseIds;

}

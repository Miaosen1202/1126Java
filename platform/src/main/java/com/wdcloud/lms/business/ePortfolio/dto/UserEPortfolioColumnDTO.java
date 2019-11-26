package com.wdcloud.lms.business.ePortfolio.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserEPortfolioColumnDTO {

    @NotNull(groups = GroupModify.class)
    private Long id;

    @NotNull(groups = GroupAdd.class)
    private Long ePortfolioId;

    @NotBlank(groups = GroupAdd.class)
    private String name;

    private String coverColor;
}

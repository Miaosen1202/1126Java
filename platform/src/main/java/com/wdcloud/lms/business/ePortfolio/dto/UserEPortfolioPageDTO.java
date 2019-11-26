package com.wdcloud.lms.business.ePortfolio.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserEPortfolioPageDTO {

    @NotNull(groups = GroupModify.class)
    private Long id;

    @NotBlank(groups = {GroupAdd.class, GroupModify.class})
    private String name;

    @NotNull(groups = GroupAdd.class)
    private Long ePortfolioColumnId;

}

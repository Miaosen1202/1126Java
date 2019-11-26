package com.wdcloud.lms.business.ePortfolio.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserEPortfolioItemCopyDTO {

    @NotNull
    private Long id;
}

package com.wdcloud.lms.business.ePortfolio.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserEPortfolioItemShareDTO {

    @NotNull
    private Long id;

    @NotNull
    private Integer visibility;
}

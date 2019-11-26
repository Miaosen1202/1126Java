package com.wdcloud.lms.business.ePortfolio.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserEPortfolioContentDTO {

    @NotNull
    private Long ePortfolioPageId;

    @NotNull
    private String content;

    private List<String> saveFileUrls;

    private List<String> deleteFileUrls;
}

package com.wdcloud.lms.core.base.vo.ePortfolio;


import lombok.Data;

import java.util.List;

@Data
public class UserEPortfolioColumnVO {

    private Long id;

    private String name;

    private String coverColor;

    private List<UserEPortfolioPageVO> pages;
}

package com.wdcloud.lms.core.base.vo.ePortfolio;


import lombok.Data;

import java.util.List;

@Data
public class UserEPortfolioVO {

    private Long id;

    private String name;

    private List<UserEPortfolioColumnListVO> columns;
}

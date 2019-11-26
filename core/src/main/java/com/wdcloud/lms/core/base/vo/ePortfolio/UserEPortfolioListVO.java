package com.wdcloud.lms.core.base.vo.ePortfolio;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserEPortfolioListVO {

    private Long id;

    private String name;

    private Integer columnCount;

    private Date updateTime;

}

package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioListVO;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioVO;

import java.util.List;

public interface UserEPortfolioExtMapper {
    List<UserEPortfolioListVO> getByUserId(Long userId);

    UserEPortfolioVO getById(Long id);
}

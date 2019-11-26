package com.wdcloud.lms.core.base.mapper.ext;

public interface UserEPortfolioContentExtMapper {

    void deleteByUserEPortfolioId(Long ePortfolioId);

    void deleteByUserEPortfolioColumnId(Long ePortfolioColumnId);

    void deleteByUserEPortfolioPageId(Long ePortfolioPageId);
}

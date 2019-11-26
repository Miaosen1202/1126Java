package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioColumnListVO;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioColumnVO;

import java.util.List;

public interface UserEPortfolioColumnExtMapper {

    UserEPortfolioColumnVO getById(Long id);

    Integer getMaxSeqByEPortfolioId(Long ePortfolioId);

    List<UserEPortfolioColumnListVO> getByEPortfolioId(Long ePortfolioId);

    void batchUpdateSeq(List<UserEPortfolioColumn> userEPortfolioColumns);
}

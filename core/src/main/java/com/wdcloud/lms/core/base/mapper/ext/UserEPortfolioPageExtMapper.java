package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioPageVO;

import java.util.List;

public interface UserEPortfolioPageExtMapper {

    List<UserEPortfolioPageVO> getByEPortfolioColumnId(Long  ePortfoliosColumnId);

    void deleteByUserEPortfolioId(Long ePortfolioId);

    void deleteByUserEPortfolioColumnId(Long ePortfolioColumnId);

    Integer getMaxSeqByEPortfolioColumnId(Long ePortfolioColumnId);

    void batchUpdateSeq(List<UserEPortfolioPage> ePortfolioPages);

    List<Long> getByEPortfolioId(Long ePortfolioId);
}

package com.wdcloud.lms.base.service;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioColumnDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EPortfolioColumnCopyService extends CopyServiceAbstract{

    @Autowired
    private UserEPortfolioDao userEPortfolioDao;
    @Autowired
    private UserEPortfolioPageDao userEPortfolioPageDao;
    @Autowired
    private UserEPortfolioColumnDao userEPortfolioColumnDao;

    @Autowired
    private EPortfolioPageCopyService ePortfolioPageCopyService;

    @Override
    public Long copy(Long id) {
        UserEPortfolioColumn originColumn = userEPortfolioColumnDao.get(id);

        UserEPortfolioColumn uerEPortfolioColumn = BeanUtil.beanCopyProperties(originColumn,
                UserEPortfolioColumn.class, Constants.IGNORE_E_PORTFOLIO_COLUMN_COPY);
        Integer maxSeq = userEPortfolioColumnDao.getMaxSeqByEPortfolioId(uerEPortfolioColumn.getEPortfolioId());
        //该方法仅为在页面集的页面进行复制，此时一个电子学档下一定存在页面集，所以maxSeq不存在null的情况
        uerEPortfolioColumn.setSeq(++maxSeq);
        userEPortfolioColumnDao.save(uerEPortfolioColumn);

        List<UserEPortfolioPage> originPages = userEPortfolioPageDao.getByEPortfolioColumnId(originColumn.getId());
        for(UserEPortfolioPage userEPortfolioPage : originPages){
            ePortfolioPageCopyService.copy(userEPortfolioPage.getId(), uerEPortfolioColumn.getId());
        }

        UserEPortfolio userEPortfolio = userEPortfolioDao.get(originColumn.getEPortfolioId());
        userEPortfolio.setTotalPage(userEPortfolio.getTotalPage()+1);
        userEPortfolioDao.update(userEPortfolio);

        return uerEPortfolioColumn.getId();
    }
}

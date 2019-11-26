package com.wdcloud.lms.base.service;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioContentDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioContent;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EPortfolioPageCopyService extends CopyServiceAbstract{

    @Autowired
    public UserEPortfolioPageDao userEPortfolioPageDao;
    @Autowired
    public UserEPortfolioContentDao userEPortfolioContentDao;

    @Autowired
    private EPortfolioPageFileService ePortfolioPageFileService;

    @Override
    public Long copy(Long id) {
        return this.copy(id, null);
    }

    /**
     *
     * @param id
     * @param ePortfolioColumnId ePortfolioColumnId为null时，为复制页面；反之则是复制页面集的情况下的复制页面
     * @return
     */
    public Long copy(Long id, Long ePortfolioColumnId) {
        UserEPortfolioPage originPage = userEPortfolioPageDao.get(id);
        if(Objects.isNull(ePortfolioColumnId)){
            ePortfolioColumnId = originPage.getEPortfolioColumnId();
        }
        Integer maxSeq = userEPortfolioPageDao.getMaxSeqByEPortfolioColumnId(ePortfolioColumnId);

        UserEPortfolioPage userEPortfolioPage = BeanUtil.beanCopyProperties(originPage,
                UserEPortfolioPage.class, Constants.IGNORE_E_PORTFOLIO_PAGE_COPY);
        userEPortfolioPage.setSeq(Objects.isNull(maxSeq) ? 1 : ++maxSeq);
        if(Objects.nonNull(ePortfolioColumnId)){
            userEPortfolioPage.setEPortfolioColumnId(ePortfolioColumnId);
        }
        userEPortfolioPageDao.save(userEPortfolioPage);

        UserEPortfolioContent originContent = userEPortfolioContentDao.getByEPortfoliosPageId(originPage.getId());
        if(Objects.nonNull(originContent)){
            UserEPortfolioContent userEPortfolioContent = BeanUtil.beanCopyProperties(originContent,
                    UserEPortfolioContent.class, Constants.IGNORE_E_PORTFOLIO_CONTENT_COPY);
            userEPortfolioContent.setEPortfolioPageId(userEPortfolioPage.getId());
            userEPortfolioContentDao.save(userEPortfolioContent);
        }

        ePortfolioPageFileService.copy(originPage.getId(), userEPortfolioPage.getId());

        return userEPortfolioPage.getId();
    }
}

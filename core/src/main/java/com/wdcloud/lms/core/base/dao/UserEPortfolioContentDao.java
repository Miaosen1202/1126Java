package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserEPortfolioContentExtMapper;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.lms.core.base.model.UserEPortfolioContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserEPortfolioContentDao extends CommonDao<UserEPortfolioContent, Long> {

    @Autowired
    private UserEPortfolioContentExtMapper userEPortfolioContentExtMapper;

    @Override
    protected Class<UserEPortfolioContent> getBeanClass() {
        return UserEPortfolioContent.class;
    }

    public void deleteByUserEPortfolioId(Long ePortfolioId) {
        userEPortfolioContentExtMapper.deleteByUserEPortfolioId(ePortfolioId);
    }

    public void deleteByUserEPortfoliosColumnId(Long ePortfolioColumnId) {
        userEPortfolioContentExtMapper.deleteByUserEPortfolioColumnId(ePortfolioColumnId);
    }

    public void deleteByUserEPortfolioPageId(Long ePortfolioPageId) {
        userEPortfolioContentExtMapper.deleteByUserEPortfolioPageId(ePortfolioPageId);
    }

    public UserEPortfolioContent getByEPortfoliosPageId(Long ePortfoliosPageId) {
        UserEPortfolioContent ePortfolioContent = UserEPortfolioContent.builder()
                .ePortfolioPageId(ePortfoliosPageId).build();

        return findOne(ePortfolioContent);
    }
}
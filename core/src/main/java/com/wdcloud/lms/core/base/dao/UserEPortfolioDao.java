package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserEPortfolioExtMapper;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioListVO;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEPortfolioDao extends CommonDao<UserEPortfolio, Long> {

    @Autowired
    private UserEPortfolioExtMapper userEPortfolioExtMapper;

    @Override
    protected Class<UserEPortfolio> getBeanClass() {
        return UserEPortfolio.class;
    }

    public List<UserEPortfolioListVO> getByUserId(Long userId) {
        return userEPortfolioExtMapper.getByUserId(userId);
    }

    public UserEPortfolioVO getById(Long id) {
        return userEPortfolioExtMapper.getById(id);
    }
}
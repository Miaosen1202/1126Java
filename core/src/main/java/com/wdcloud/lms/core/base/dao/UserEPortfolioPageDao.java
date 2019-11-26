package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserEPortfolioPageExtMapper;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserEPortfolioPageDao extends CommonDao<UserEPortfolioPage, Long> {

    @Autowired
    private UserEPortfolioPageExtMapper userEPortfolioPageExtMapper;

    @Override
    protected Class<UserEPortfolioPage> getBeanClass() {
        return UserEPortfolioPage.class;
    }

    public void deleteByUserEPortfolioId(Long ePortfolioId) {
        userEPortfolioPageExtMapper.deleteByUserEPortfolioId(ePortfolioId);
    }

    public void deleteByUserEPortfolioColumnId(Long ePortfolioColumnId) {
        userEPortfolioPageExtMapper.deleteByUserEPortfolioColumnId(ePortfolioColumnId);
    }

    public Integer getMaxSeqByEPortfolioColumnId(Long ePortfolioColumnId) {
        return userEPortfolioPageExtMapper.getMaxSeqByEPortfolioColumnId(ePortfolioColumnId);
    }

    public List<UserEPortfolioPage> getByEPortfolioColumnId(Long ePortfolioColumnId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(UserEPortfolioPage.E_PORTFOLIO_COLUMN_ID, ePortfolioColumnId);
        example.setOrderByClause(" seq asc ");

        return find(example);
    }

    public void batchUpdateSeq(List<UserEPortfolioPage> ePortfolioPages) {
        userEPortfolioPageExtMapper.batchUpdateSeq(ePortfolioPages);
    }

    public List<Long> getByEPortfolioId(Long ePortfolioId) {
        List<Long> result = new ArrayList<>();

        List<Long> userEPortfolioPageIds = userEPortfolioPageExtMapper.getByEPortfolioId(ePortfolioId);
        result.addAll(userEPortfolioPageIds);

        return result;
    }
}
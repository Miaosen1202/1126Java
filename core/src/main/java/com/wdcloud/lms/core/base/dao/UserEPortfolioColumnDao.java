package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserEPortfolioColumnExtMapper;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioColumnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class UserEPortfolioColumnDao extends CommonDao<UserEPortfolioColumn, Long> {

    @Autowired
    private UserEPortfolioColumnExtMapper userEPortfolioColumnExtMapper;

    @Override
    protected Class<UserEPortfolioColumn> getBeanClass() {
        return UserEPortfolioColumn.class;
    }

    public UserEPortfolioColumnVO getById(Long id) {
        UserEPortfolioColumnVO vo = userEPortfolioColumnExtMapper.getById(id);
        return vo;
    }

    public void deleteByEPortfolioId(Long userEPortfolioId) {
        UserEPortfolioColumn userEPortfolioColumn = UserEPortfolioColumn.builder().ePortfolioId(userEPortfolioId).build();
        this.deleteByField(userEPortfolioColumn);
    }

    public Integer getMaxSeqByEPortfolioId(Long userEPortfolioId){
        return userEPortfolioColumnExtMapper.getMaxSeqByEPortfolioId(userEPortfolioId);
    }

    public List<UserEPortfolioColumn> getByEPortfolioId(Long userEPortfolioId){
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(UserEPortfolioColumn.E_PORTFOLIO_ID, userEPortfolioId);
        example.setOrderByClause(" seq asc ");

        return find(example);
    }

    public void batchUpdateSeq(List<UserEPortfolioColumn> userEPortfolioColumns) {
        userEPortfolioColumnExtMapper.batchUpdateSeq(userEPortfolioColumns);
    }
}

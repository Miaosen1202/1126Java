package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserEPortfolioPageFileExtMapper;
import com.wdcloud.lms.core.base.model.UserEPortfolioPageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class UserEPortfolioPageFileDao extends CommonDao<UserEPortfolioPageFile, Long> {

    @Autowired
    private UserEPortfolioPageFileExtMapper userEPortfolioPageFileExtMapper;

    @Override
    protected Class<UserEPortfolioPageFile> getBeanClass() {
        return UserEPortfolioPageFile.class;
    }

    public List<UserEPortfolioPageFile> getByEPortfolioPageId(Long ePortfolioPageId) {
        List<UserEPortfolioPageFile> result = new ArrayList<>();

        UserEPortfolioPageFile userEPortfolioPageFile = UserEPortfolioPageFile.builder()
                .ePortfolioPageId(ePortfolioPageId).build();
        List<UserEPortfolioPageFile> userEPortfolioPageFiles = find(userEPortfolioPageFile);

        result.addAll(userEPortfolioPageFiles);
        return result;
    }

    public Set<Long> getByUserFileIdIn(List<Long> userFileIds) {
        return userEPortfolioPageFileExtMapper.getByUserFileIdIn(userFileIds);
    }

    public void delete(Long ePortfolioPageId, List<Long> userFileIds) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(UserEPortfolioPageFile.E_PORTFOLIO_PAGE_ID, ePortfolioPageId)
                .andIn(UserEPortfolioPageFile.USER_FILE_ID, userFileIds);

        delete(example);
    }
}
package com.wdcloud.lms.core.base.dao;

import com.github.pagehelper.PageHelper;
import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.SisImport;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository
public class SisImportDao extends CommonDao<SisImport, Long> {

    public SisImport findLastImport(Long userId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(SisImport.OP_USER_ID, userId);
        example.setOrderByClause(" create_time desc ");
        PageHelper.startPage(1,1);
        return findOne(example);
    }

    @Override
    protected Class<SisImport> getBeanClass() {
        return SisImport.class;
    }
}
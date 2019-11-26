package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.TermExtMapper;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.vo.TermAndConfigVo;
import com.wdcloud.lms.core.base.vo.TermListVO;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class TermDao extends CommonDao<Term, Long> {
    @Autowired
    private TermExtMapper termExtMapper;
    @Autowired
    private OrgDao orgDao;

    public List<TermAndConfigVo> findTermAndConfigs(List<Long> termIds) {
        if (ListUtils.isEmpty(termIds)) {
            return new ArrayList<>();
        }
        return termExtMapper.findTermAndConfigs(termIds);
    }

    public List<Term> findBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        return termExtMapper.findUserOrgTerms(rootOrgTreeId, sisIds);
    }

    public void deleteBySisIds(String rootOrgTreeId, Collection<String> sisIds) {
        if (sisIds == null || sisIds.isEmpty()) {
            return;
        }
        Org rootOrg = orgDao.getByTreeId(rootOrgTreeId);
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(Term.ORG_ID, rootOrg.getId())
                .andIn(Term.SIS_ID, sisIds);
        delete(example);
    }


    public List<TermListVO> termList(String rootTreeId) {
        return termExtMapper.termList(rootTreeId);
    }

    public Term findDefaultTerm(Long orgId) {
        return findOne(Term.builder().orgId(orgId).isDefault(Status.YES.getStatus()).build());
    }


    @Override
    protected Class<Term> getBeanClass() {
        return Term.class;
    }
}
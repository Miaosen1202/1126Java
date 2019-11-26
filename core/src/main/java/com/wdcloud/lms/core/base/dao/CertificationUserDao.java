package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.CertificationUserExtMapper;
import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.vo.certification.CertificationUserVO;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CertificationUserDao extends CommonDao<CertificationUser, Long> {

    @Autowired
    private CertificationUserExtMapper certificationUserExtMapper;

    @Override
    protected Class<CertificationUser> getBeanClass() {
        return CertificationUser.class;
    }

    public int batchSave(List<CertificationUser> certificationUserList) {
        return certificationUserExtMapper.batchSave(certificationUserList);
    }

    public List<CertificationUserVO> findCertificationUserListByAdmin(Map<String, Object> params) {
        return certificationUserExtMapper.findCertificationUserListByAdmin(params);
    }

    public List<LearnerCertificationVO> findCertListForSync(Integer type, Integer status) {
        return certificationUserExtMapper.findCertListForSync(type,status);
    }
}
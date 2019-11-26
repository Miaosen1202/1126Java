package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.CertificationExtMapper;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.vo.certification.CertificationVO;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CertificationDao extends CommonDao<Certification, Long> {

    @Autowired
    private CertificationExtMapper certificationExtMapper;

    @Override
    protected Class<Certification> getBeanClass() {
        return Certification.class;
    }

    public List<CertificationVO> findCertificationListByAdmin(Map<String, Object> params) {
        return certificationExtMapper.findCertificationListByAdmin(params);
    }

    public List<LearnerCertificationVO> findCertificationListByLearner(Map<String, Object> params) {
        List<LearnerCertificationVO> certificationVOList= certificationExtMapper.findCertificationListByLearner(params);
        setDueTime(certificationVOList);
        return certificationVOList;
    }

    /**
     * 构建截止日期
     * @param certificationVOList
     */
    private void setDueTime(List<LearnerCertificationVO> certificationVOList) {
        if (ListUtils.isNotEmpty(certificationVOList)) {
            certificationVOList.forEach(vo->{
                vo.setDueTime(DateUtil.daysOperation(vo.getEnrollTime(), vo.getOpDay()));
            });
        }
    }

    public LearnerCertificationVO findOneCertificationListByLearner(Long certificationId, Long userId) {
        LearnerCertificationVO vo = certificationExtMapper.findOneCertificationListByLearner(certificationId, userId);
        setDueTime(List.of(vo));
        return vo;
    }
}
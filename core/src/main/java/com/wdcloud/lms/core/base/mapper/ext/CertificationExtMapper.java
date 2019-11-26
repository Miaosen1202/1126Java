package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.certification.CertificationVO;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CertificationExtMapper {
    List<CertificationVO> findCertificationListByAdmin(Map<String, Object> params);

    List<LearnerCertificationVO> findCertificationListByLearner(Map<String, Object> params);

    LearnerCertificationVO findOneCertificationListByLearner(@Param("certificationId") Long certificationId, @Param("userId") Long userId);
}
